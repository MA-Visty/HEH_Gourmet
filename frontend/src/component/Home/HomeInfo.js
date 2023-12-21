import BulbImage from "../../assets/images/bulb.svg";

function HomeInfo() {
    return (
        <img style={{objectFit: 'cover', display:"flex", marginLeft:"auto", marginRight:"auto", height:350}} src={BulbImage} alt="error" />
    );
}

export default HomeInfo;