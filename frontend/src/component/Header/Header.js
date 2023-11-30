import React, {useState} from "react";
import { Container, Nav, Navbar } from "react-bootstrap";
import { useAppContext } from "../../store/AppContext";
import { LinkContainer } from "react-router-bootstrap";
import OffCanvasUser from "../OffCanvas/OffCanvasUser";
import OffCanvasCart from "../OffCanvas/OffCanvasCart";

function Header(props) {
    const [showUser, setShowUser] = useState(false);
    const [showCart, setShowCart] = useState(false);
    const handleUserClose = () => setShowUser(false);
    const handleCartClose = () => setShowCart(false);
    const handleUserShow = () => setShowUser(true);
    const handleCartShow = () => setShowCart(true);

    return (
        <>
            <Navbar bg="light" expand="lg">
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
                            <LinkContainer to="/Menu">
                                <Nav.Link>Menu</Nav.Link>
                            </LinkContainer>
                        </Nav>
                        <img src="/cart.svg" alt="error" width={25} onClick={handleCartShow}></img>
                        <img src="/user.svg" alt="error" width={25} onClick={handleUserShow}></img>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
            <OffCanvasCart show={showCart} onHide={handleCartClose} />
            <OffCanvasUser show={showUser} onHide={handleUserClose} />
        </>
    );
}
export default Header;