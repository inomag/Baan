import React, { Fragment } from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faQuestionCircle } from '@fortawesome/free-solid-svg-icons'

import './NotFound.css';

export const NotFound = ({ query, handleChange, city }) => {
    return (
        <Fragment>
            <div className="mainbox">
                <div className="err">4</div>
                <FontAwesomeIcon className="far" icon={faQuestionCircle} />
                <div className="err2">4</div>
                <div className="msg">
                    <p className='text-danger'>
                        Maybe this page moved?
                        Never existed in the first place?
                    </p>
                    <p>
                        Go back to <Link id="link_to_home" to="/">home</Link>
                    </p>
                </div>
            </div>
        </Fragment>
    )
}