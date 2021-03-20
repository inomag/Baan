import { Icon } from '@iconify/react';
import mapMarkerAlert from '@iconify-icons/mdi/map-marker-alert';

const LocationMarker = ({ lat, lng, onClick }) => {
    return (
        <div className="location-marker" onClick={onClick}>
            <Icon
                icon={mapMarkerAlert}
                className="location-icon"
            />
        </div>
    )
}

export default LocationMarker;