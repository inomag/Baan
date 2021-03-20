import React, { Fragment, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

import AlertMessage from '../../components/AlertMessage';

const SigninPhoneInput = (props) => {
	const { value, handleChange } = props;
	const [errorMessage, setErrroMessage] = useState('');
	const [successMessage, setSuccessMessage] = useState('');

	const Continue = (e) => {
		axios.post('http://localhost:5000/api/signup', {
				phone: `${value.phone}`
			})
			.then(function (res) {
				console.log(res);
				if(res.status === 200){
					setSuccessMessage(res.data.success);
				}
			});

		e.preventDefault();
		props.nextStep();
	};
	return (
		<Fragment>

			<div className="container">

			<AlertMessage msg={errorMessage} type="danger" ></AlertMessage>
            <AlertMessage msg={successMessage} type="success" ></AlertMessage>

				<form>
					<div className="mb-3">
						<label className="form-label">Phone No.</label>
						<input
							type="number"
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

					<button onClick={Continue} className="btn btn-dark">
						Send OTP
					</button>
				</form>

				<p><Link id='login_link' to="/signup">Don't have an account? Signup here</Link></p>
			</div>

		</Fragment>

	);
}

export default SigninPhoneInput;