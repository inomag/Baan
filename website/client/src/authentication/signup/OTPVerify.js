import React, { Fragment, useState } from 'react';
import axios from 'axios';
import { authenticate } from '../authorize';
import { Redirect } from 'react-router-dom';
import AlertMessage from '../../components/AlertMessage';

function OtpVerify(props) {
	const [error, setError] = useState({
		error: '',
		success: ''
	});
	const [errorMessage, setErrroMessage] = useState('');
	const [successMessage, setSuccessMessage] = useState('');
	const [redirect, setRedirect] = useState(false);
	const { value, handleChange } = props;
	const back = (e) => {
		e.preventDefault();
		props.prevStep();
	};

	const { phone, code, name, default_loc, role } = value;

	console.log(phone, code, name, default_loc, role);

	const confirmOtp = () => {
		axios.post('http://localhost:5000/api/verifyOTP', {
			phone: `${value.phone}`,
			code: `${value.code}`,
			username: `${value.name}`,
			default_loc: `${value.default_loc}`,
			// role: `${value.role}`,
		})
			.then(function (res) {
				console.log(res);

				if (res.data.user) {
					authenticate(res.data.user, () => {
						console.log(res.data.user);
						setRedirect(true);
						setSuccessMessage(res.data.success);
						setError({ ...error, success: res.data.user });
					});
				} else {
					setErrroMessage(res.error);
				}
			})
			.catch(function (error) {
				console.log(error.response);
				setError({ ...error, error: error.response.data.msg });
			});
	};

	return (
		<Fragment>

			{redirect && (
				<Redirect to='/' />
			)}

			<div className="container">
				<AlertMessage msg={errorMessage} type="danger" ></AlertMessage>
				<AlertMessage msg={successMessage} type="success" ></AlertMessage>

				<form>
					<div className="mb-3">
						<label className="form-label">Name</label>
						<input
							type="text"
							value={value.name}
							onChange={handleChange('name')}
							className="form-control"
							required="required"
							placeholder="Enter name" />
					</div>

					<div className="mb-3">
						<label className="form-label">Phone No.</label>
						<input
							type="tel"
							value={value.phone}
							onChange={handleChange('phone')}
							className="form-control"
							required="required"
							placeholder="Enter phone number" />
					</div>

					<div className="mb-3">
						<label className="form-label">Default Location</label>
						<input
							type="text"
							value={value.default_loc}
							onChange={handleChange('default_loc')}
							className="form-control"
							required="required"
							placeholder="Upload your location" />
					</div>

					{/* <div className="mb-3">
						<label className="form-label">Role</label>
						<input
							type="text"
							value={value.role}
							className="form-control"
							required="required"
							placeholder="Role" />
					</div> */}

					<div className="mb-3">
						<label className="form-label">OTP</label>
						<input
							type="text"
							value={value.code}
							onChange={handleChange('code')}
							className="form-control"
							required="required"
							placeholder="Enter OTP" />
					</div>

				</form>

				<div class="d-grid gap-2 d-md-block">
					<button onClick={back} className="btn btn-outline-dark m-2">
						Back
				</button>
					<button onClick={confirmOtp} className="btn btn-outline-dark m-3">
						Confirm OTP
				</button>
				</div>

			</div>
		</Fragment>

	);
}

export default OtpVerify;
