import React, { Component } from "react";

export default class Login extends Component {

    render() {
        return (
            <div className="auth-wrapper">
        <div className="auth-inner">
            <form>
                <h3>Login</h3>

                <div className="form-group">
                    <label>Phone Number</label>
                    <input type="email" className="form-control" placeholder="Enter Number" />
                </div>

                <div className="form-group">
                    <label>Location</label>
                    <input type="password" className="form-control" placeholder="Enter Location" />
                </div>

                <div className="form-group">
                    <div className="custom-control custom-checkbox">
                        <input type="checkbox" className="custom-control-input" id="customCheck1" />
                        <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
                    </div>
                </div>

                <button type="Enter" className="btn btn-primary btn-block">Send OTP</button>
                <p className="forgot-password text-right">
                    {/* Forgot <a href="./home.js">password?</a> */}
                </p>
            </form>
            </div>
            </div>
        );
    }
}