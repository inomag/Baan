  
import React, { Fragment, useState, useEffect } from 'react';

const UserCollection = ({ users, floodedLocations }) => {
    const [affectedUsers, setAffectedUsers] = useState([]);

    useEffect(() => {

    }, []);

    return (
        <Fragment>
            <h2>Compare Distance</h2>
            <button className="btn btn-secondary">
                Notify all users
            </button>
        </Fragment >
    )
}

export default UserCollection;