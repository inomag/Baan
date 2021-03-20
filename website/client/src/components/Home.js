import React, { Fragment, useState, useEffect } from "react";
import GoogleMap from "../admin/GoogleMap";

const Home = () => {
    const [floodedLocations, setFloodedLocations] = useState([]);
    const [users, setUsers] = useState([]);
    const [latitude, setLatitude] = useState('');
	const [longitude, setLongitude] = useState('');

    const fetchAllDisasters = () => {
        return fetch('http://localhost:5000/api/get/locations')
            .then(res => {
                return res.json();
            });
    }

    const fetchAllUsers = () => {
        return fetch('http://localhost:5000/api/get/all/users')
            .then(res => {
                return res.json();
            });
    }

    useEffect(() => {

        if (navigator.geolocation) {
            navigator.geolocation.watchPosition(function (position) {
                // console.log("Latitude is :", position.coords.latitude);
                // console.log("Longitude is :", position.coords.longitude);
                setLatitude(position.coords.latitude);
                setLongitude(position.coords.longitude)
            });
        }

        fetchAllDisasters().then(data => {
            setFloodedLocations(data.flooded_locations);
        });
        fetchAllUsers().then(data => {
            setUsers(data.users);
        });
    }, []);
    return (
        <Fragment>
            <div className="container">
                <GoogleMap
                    floodedLocations={floodedLocations}
                    users={users}
                />
            </div>
        </Fragment>
    );
}

export default Home;