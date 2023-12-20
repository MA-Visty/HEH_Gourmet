import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Table from "react-bootstrap/Table";
import TrashImage from '../../assets/images/trash.svg';

import { useAppContext, useDispatchContext } from "../../store/AppContext";

const Cart = (props) => {
    const { state } = useAppContext();
    const { dispatch } = useDispatchContext();
    let total = state.price;
    let res = state.cart.map((element) => (
        <tr key={element.product._id}>
            <td>{element.product.name}</td>
            <td>{element.quantity}</td>
            <td>{element.product.price} €</td>
            <td>
                <img
                    src={TrashImage}
                    alt="error"
                    width={20}
                    onClick={() => dispatch({ type: "remove", id: element.product.id })}
                ></img>
            </td>
        </tr>
    ));
    return (
        <Modal show={true} onHide={props.hide} backdrop="static" keyboard={false}>
            <Modal.Header closeButton>
                <Modal.Title>Modal title</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Table striped bordered hover>
                    <thead>
                    <tr>
                        <th>name</th>
                        <th>quantity</th>
                        <th>prices</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>{res}</tbody>
                </Table>
                <h2>Total: {total} €</h2>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={props.hide}>
                    Close
                </Button>
                <Button variant="primary">Understood</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default Cart;