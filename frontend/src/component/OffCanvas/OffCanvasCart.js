import Offcanvas from "react-bootstrap/Offcanvas";

function OffCanvasCart({ show, onHide, ...props }) {
    return (
        <Offcanvas show={show} onHide={onHide} placement={'end'} {...props}>
            <Offcanvas.Header closeButton>
                <Offcanvas.Title>Cart</Offcanvas.Title>
            </Offcanvas.Header>
            <Offcanvas.Body>
                Some text as a placeholder. In real life, you can have the elements you have chosen, such as text, images, lists, etc.
            </Offcanvas.Body>
        </Offcanvas>
    );
}

export default OffCanvasCart;