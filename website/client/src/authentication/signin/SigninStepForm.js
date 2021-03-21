import React, { useState, useEffect } from 'react';

import SigninOTPVerify from './SigninOTPVerify';
import SigninPhoneInput from './SigninPhoneInput';

const SigninStepForm = () => {
	const [state, setState] = useState({
		phone: '',
		code: '',
		role: '1'
	});
	
	const [step, setStep] = useState(1);

	const handleChange = (input) => (e) => {
		setState({ ...state, [input]: e.target.value });
	};

	const nextStep = () => {
		setStep(prevStep => prevStep + 1)
	};

	const prevStep = () => {
		setStep(prevStep => prevStep - 1)
	};

	const { phone, code, role } = state;
	const value = { phone, code, role };

	switch (step) {
		case 1:
			return <SigninPhoneInput nextStep={nextStep} handleChange={handleChange} value={value} />;
		case 2:
			return <SigninOTPVerify nextStep={nextStep} prevStep={prevStep} handleChange={handleChange} value={value} />;
		default:
			return <SigninPhoneInput nextStep={nextStep} handleChange={handleChange} value={value} />

	}
};

export default SigninStepForm;