import Offcanvas from "react-bootstrap/Offcanvas";
import Cart from "../Cart/Cart";
import Button from "react-bootstrap/Button";
import {Stack} from "react-bootstrap";
import {useAppContext} from "../../store/AppContext";
import Loader from "../Loader/Loader";
import EmptyData from "../Loader/EmptyData";

function OffCanvasCart({ show, onHide, ...props }) {
    const { state } = useAppContext();
    return (
        <Offcanvas show={show} onHide={onHide} placement={'end'} {...props}>
            <Offcanvas.Header closeButton>
                <Offcanvas.Title>Cart</Offcanvas.Title>
            </Offcanvas.Header>
            <Offcanvas.Body>
                <Stack align="end">
                    {state.quantity > 0 ?
                        <Cart />
                        : <EmptyData />
                    }
                    <Button variant={state.quantity > 0 ? "primary" : "outline-primary"}
                            disabled={state.quantity === 0}
                            size="lg">
                        Pay
                    </Button>
                </Stack>
            </Offcanvas.Body>
        </Offcanvas>
    );
}

export default OffCanvasCart;