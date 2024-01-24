import Offcanvas from "react-bootstrap/Offcanvas";
import Cart from "../Cart/Cart";
import Button from "react-bootstrap/Button";
import {Stack} from "react-bootstrap";
import {useAppContext, useDispatchContext} from "../../store/AppContext";
import EmptyData from "../Loader/EmptyData";
import axios from "axios";
import API_URL from "../../apiConfig";

function OffCanvasCart({ show, onHide, ...props }) {
    const { state } = useAppContext();
    const { dispatch } = useDispatchContext();

    const order = async (event) => {
        try {
            const currentDate = new Date();
            const formattedDate = currentDate.toISOString();
            const response = await axios
                .post(`${API_URL}/api/cart/${state.user.id}/checkout`, {
                    orderDate: formattedDate,
                });
            if(response.status === 201) {
                dispatch({ type: "removeAll" })
            }
        } catch (error) {
            console.debug(error)
        }
    }

    return (
        <Offcanvas show={show} onHide={onHide} placement={'end'} {...props}>
            <Offcanvas.Header closeButton>
                <Offcanvas.Title>Panier</Offcanvas.Title>
            </Offcanvas.Header>
            <Offcanvas.Body>
                <Stack align="end">
                    {state.quantity > 0 ?
                            <Cart />
                    :
                            <EmptyData />
                    }
                    <Button onClick={order}
                            variant={state.quantity > 0 ? "primary" : "outline-primary"}
                            disabled={state.quantity === 0}
                            size="lg">
                        <strong style={{position: "relative"}}>Payer</strong>
                        {state.price > 0 ?
                            <span style={{
                                fontSize: "0.75em",
                                fontStyle: "oblique",
                                position: "absolute",
                                translate: "5px 6px"
                            }}>({state.price.toFixed(2)}€)</span>
                            : ""
                        }
                    </Button>
                </Stack>
            </Offcanvas.Body>
        </Offcanvas>
    );
}

export default OffCanvasCart;