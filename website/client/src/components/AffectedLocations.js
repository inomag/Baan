import React, { Fragment, useState, useEffect } from "react";

const AffectedLocations = () => {
    const google = window.google;
    const [floodedLocations, setFloodedLocations] = useState([]);
    const [users, setUsers] = useState([]);

    const fetchAllDisasters = () => {
        return fetch('http://localhost:5000/api/get/locations')
            .then(res => {
                return res.json();
            });
    }

    const fetchAffectedUsers = () => {
        return fetch('http://localhost:5000/api/get/affected-user')
            .then(res => {
                return res.json();
            });
    }

    useEffect(() => {
        fetchAllDisasters().then(data => {
            console.log(data);
            setFloodedLocations(data.flooded_locations);
        });

        fetchAffectedUsers().then(data => {
            console.log(data);
            setUsers(data.users);
        });
    }, []);

    return (
        <Fragment>

            <div className="container">

                <h3>Total affected users: {users.length}</h3>
                {users.map(user => (

                    <Fragment>
                        <ul className="list-group list-group-horizontal">
                            <li className="list-group-item" >{user.name}</li>
                            <li className="list-group-item">{user.phone}</li>
                        </ul>
                    </Fragment>

                ))}
                <hr />
                
                <h3>Total affected locations: {floodedLocations.length}</h3>
                {
                    floodedLocations.map(flood => (
                        <ul className="list-group list-group-horizontal">
                            <Fragment>
                                <h4 className="list-group-item">{flood.level}</h4>
                                <li>
                                    {flood.level === 1 ? <h5 className='text-success m-2'>Mild affected area</h5> :
                                        flood.level === 2 ? <h5 className='text-secondary m-2'>Awareness required</h5>
                                            : flood.level === 3 ? <h5 className='text-danger m-2'>Dangerour area</h5> : ''
                                    }
                                </li>
                            </Fragment>
                        </ul>
                    ))
                }

            </div>
        </Fragment >
    );
}

export default AffectedLocations;