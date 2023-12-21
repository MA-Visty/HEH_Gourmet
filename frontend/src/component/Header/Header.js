import React, {useState} from "react";
import { Container, Nav, Navbar, Badge, Row, Col } from "react-bootstrap";
import { useAppContext } from "../../store/AppContext";
import { LinkContainer } from "react-router-bootstrap";
import OffCanvasUser from "../OffCanvas/OffCanvasUser";
import OffCanvasCart from "../OffCanvas/OffCanvasCart";
import CartImage from '../../assets/images/cart.svg';
import UserImage from '../../assets/images/user.svg';
import UserLogImage from '../../assets/images/userComplet.svg';

function Header() {
    const [showUser, setShowUser] = useState(false);
    const [showCart, setShowCart] = useState(false);
    const handleUserClose = () => setShowUser(false);
    const handleCartClose = () => setShowCart(false);
    const handleUserShow = () => setShowUser(true);
    const handleCartShow = () => setShowCart(true);

    const [userLog, setUserLog] = useState(false);
    const [cartContain, setCartContain] = useState(false);

    return (
        <>
            <Navbar bg="light" expand="lg" sticky={"top"}>
                <Container>
                    <LinkContainer to="/">
                        <Navbar.Brand>HEH-Gourmet</Navbar.Brand>
                    </LinkContainer>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto">
                            <LinkContainer to="/">
                                <Nav.Link>Home</Nav.Link>
                            </LinkContainer>
                            <LinkContainer to="/menu">
                                <Nav.Link>Menu</Nav.Link>
                            </LinkContainer>
                            <LinkContainer to="/workspace">
                                <Nav.Link>Workspace</Nav.Link>
                            </LinkContainer>
                        </Nav>
                        <Row>
                            <Col xs="auto">
                                <img src={CartImage} alt="error" width={30} onClick={handleCartShow} />
                                <Badge bg="primary" pill>
                                    14
                                </Badge>
                            </Col>
                            <Col xs="auto">
                                <img src={userLog ? UserLogImage : UserImage} alt="error" width={30} onClick={handleUserShow} />
                            </Col>
                        </Row>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
            <OffCanvasCart show={showCart} onHide={handleCartClose} type={cartContain}/>
            <OffCanvasUser show={showUser} onHide={handleUserClose} type={""}/>
        </>
    );
}
export default Header;