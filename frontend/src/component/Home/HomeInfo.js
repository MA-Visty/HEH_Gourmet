import BulbImage from "../../assets/images/bulb.svg";
import {Col, Row} from "react-bootstrap";
import {Link} from "react-router-dom";

function HomeInfo() {
    return (
        <Row style={{marginLeft: "10%", marginTop: "2%", marginRight: "10%"}}>
            <Col sm={4}>
                <img style={{objectFit: 'cover', display:"flex", marginLeft:"auto", marginRight:"auto", height:150}} src={BulbImage} alt="error" />
            </Col>
            <Col sm={8} className="d-flex align-items-center">
                <p><span style={{fontWeight: "bold"}}>HEH-Gourmet</span>, une site permettant de <Link to="/Menu">commander</Link> des sandwich gourmet 😊.</p>
            </Col>
        </Row>
    );
}

export default HomeInfo;