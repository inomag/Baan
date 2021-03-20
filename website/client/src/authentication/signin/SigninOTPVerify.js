import React, { Fragment, useState } from 'react';
import axios from 'axios';
import { authenticate } from '../authorize';
import { Redirect } from 'react-router-dom';
import AlertMessage from '../../components/AlertMessage';

function SigninOTPVerify(props) {
	const [error, setError] = useState({
		error: '',
		success: ''
	});
	const [errorMessage, setErrroMessage] = useState('');
	const [successMessage, setSuccessMessage] = useState('');
	const [redirect, setRedirect] = useState(false);
	const [redirectAdmin, setRedirectAdmin] = useState(false);
	const { value, handleChange } = props;
	const back = (e) => {
		e.preventDefault();
		props.prevStep();
	};

	const { phone, code, role } = value;

	const confirmOtp = () => {
		axios.post('http://localhost:5000/api/verifyOTP', {
			phone: `${value.phone}`,
			code: `${value.code}`,
			// role: `${value.role}`
		})
			.then(res => {
				console.log(res);
				console.log(res.status);
				if (res.status === 201) {
					authenticate(res.data.user, () => {
						console.log(res.data.user);
						if (res.data.user.role === 1) {
							setRedirectAdmin(true);
						}
						setRedirect(true);
						setSuccessMessage('User logged in successfully!');
						setError({ ...error, success: res.data.error });
					});
				} else if (res.status === 200) {
					setErrroMessage('User alreadys exists!');
				} else {
					setErrroMessage('Some error occured!');
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

			{redirectAdmin && (
				<Redirect to='/' />
			)}

			<div className="container">
				<AlertMessage msg={errorMessage} type="danger" ></AlertMessage>
				<AlertMessage msg={successMessage} type="success" ></AlertMessage>

				<form>
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

				<button onClick={back} className="btn btn-outline-dark">
					Back
				</button>
				<button onClick={confirmOtp} className="btn btn-outline-dark">
					Confirm OTP
				</button>
			</div>
		</Fragment>

	);
}

export default SigninOTPVerify;
