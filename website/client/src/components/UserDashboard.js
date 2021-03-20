import React, { Fragment } from "react";
import flood from './flood.jpg';

const Dashboard = () => {

    return (
        <Fragment>
            <div className="container">
                <div class="card m-2"
                // style={{width: '300px', height='400px'}}
                >
                    <img src={flood} class="img" alt="..." />
                    <div class="card-body">
                        <h5 class="card-title text-center">Flood in Assam</h5>
                        <p class="card-text">
                            Due to its tropical monsoon climate, the state of Assam is prone to annual flooding. While the monsoons are important for the largely agriculture dependent regional economy, they now cause annually recurring destruction of infrastructure, crops, livelihoods and loss of lives. Although annual precipitation is declining, daily rainfall data shows an increase in extreme rainfall events, leading to extreme flooding in Assam. Higher temporal and spatial variability of rainfall across the regions has been attributed to the impact of climate change.
                            NASA Earth Observatory captured excessive rainfall during the month of July 2020 particularly in Assam (India) and other parts of South and East Asia. Dark red indicating amounts of 80 to 100 centimeters and above.
                            Accumulated monsoon rains during July 2020 over Assam, South and East Asia.
                        </p>
                    </div>
                </div>
            </div>
        </Fragment>
    );
}

export default Dashboard;