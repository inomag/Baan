import { Fragment, useState } from 'react';
import GoogleMapReact from 'google-map-react';

import LocationMarker from './LocationMarker';
import LocationInfoBox from './LocationInfoBox';
import UserCollection from './UserCollection';

const GoogleMap = ({ floodedLocations, center, zoom, users }) => {
    const google = window.google;
    const [locationInfo, setLocationInfo] = useState(null);

    const markers = floodedLocations.map((flood, i) => {
        const res = flood.coordinates.split(',');
        return <LocationMarker
            key={i}
            lat={res[0]}
            lng={res[1]}
            onClick={() => getAddress(res[0], res[1])}
        />
    });

    const getAddress = (lat, lng) => {
        const geocoder = new google.maps.Geocoder();
        const location = new google.maps.LatLng(lat, lng);

        geocoder.geocode({
            'latLng': location
        }, (results, status) => {
            if (status === "OK") {
                if (results) {
                    setLocationInfo(results[1].formatted_address || results[0].formatted_address);
                } else {
                    window.alert("No results found");
                }
            } else {
                window.alert("Geocoder failed due to: " + status);
            }
        });
    }

    return (
        <Fragment>
            <UserCollection floodedLocations={floodedLocations} users={users} />

            <div className="map">
                <GoogleMapReact
                    // AIzaSyA78trJmNiEoLm7zs-Di2j5H8LmfT8sGX4 -Google Place Api
                    bootstrapURLKeys={{ key: 'AIzaSyA7bN1ki27FuPWi-OaVnJZ1y5b7nTFhX_k' }}
                    defaultCenter={center}
                    defaultZoom={zoom}
                >
                    {markers}
                </GoogleMapReact>
                {locationInfo && <LocationInfoBox info={locationInfo} />}
            </div>
        </Fragment>
    )
}

GoogleMap.defaultProps = {
    center: {
        lat: 26.5105954,
        lng: 90.6228871
    },
    zoom: 10
}

export default GoogleMap;