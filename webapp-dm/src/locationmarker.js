  
import { Icon } from '@iconify/react';
import jetSkiing from '@iconify-icons/map/jet-skiing';

const LocationMarker = ({ lat, lng, onClick }) => {
    return (
        <div className="location-marker" onClick={onClick}>
            <Icon
                icon={jetSkiing}
                className="location-icon"
            />
        </div>
    )
}

export default LocationMarker;