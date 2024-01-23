import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Table from "react-bootstrap/Table";
import TrashImage from '../../assets/images/trash.svg';
import { useAppContext, useDispatchContext } from "../../store/AppContext";

function Cart() {
    const { state } = useAppContext();
    const { dispatch } = useDispatchContext();
    return (
        <Table striped bordered hover>
            <thead align={"center"}>
                <tr>
                    <th>Nom</th>
                    <th>Quantités</th>
                    <th>Prix</th>
                    <th>
                        <img
                            src={TrashImage}
                            alt="error"
                            width={20}
                            onClick={() => dispatch({ type: "removeAll" })}
                        ></img>
                    </th>
                </tr>
            </thead>
            <tbody align={"center"}>
                {state.cart.map((item) => (
                    <tr key={item.product.id}>
                        <td>{item.product.name}</td>
                        <td>{item.quantity}</td>
                        <td>{item.product.price} €</td>
                        <td>
                            <img
                                src={TrashImage}
                                alt="error"
                                width={20}
                                onClick={() => dispatch({ type: "remove", id: item.product.id })}
                            ></img>
                        </td>
                    </tr>
                ))}
            </tbody>
        </Table>
    );
};

export default Cart;