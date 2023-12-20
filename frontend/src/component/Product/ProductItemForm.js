import React, { useContext } from "react";
import { Link } from "react-router-dom";
import {Row, Col, Button} from 'react-bootstrap';
//import useAppContext from '../../store/AppContext';
import MenuImage from "../../assets/images/menu.svg";

function ProductItemForm({id, quantity}) {
    /*
    const {addCartProduct} = useContext(useAppContext);

    const AddCart = (element) => {
        if(element.target.parentNode.firstChild.value) {
            let amount = parseInt(element.target.parentNode.firstChild.value);
            if( amount > 0 ) {
                data.map((item) => {
                    if (item._id == element.target.parentNode.parentNode.parentNode.id) {
                        addCartProduct(item, amount);
                        element.target.parentNode.firstChild.value = 0;
                    }
                });
            }
        }
    };
     */

	return (
        <Row className="d-flex justify-content-center">
            <Col>
                <Button variant="outline-secondary" as={Link} to={`/product/${id}`}>
                    <img style={{objectFit:'cover', display:"flex", height:"1.6rem", width:"auto"}} src={MenuImage} alt="error" />
                </Button>
            </Col>
            <Col>
                <Button variant="primary" className="w-100">
                    Add
                </Button>
            </Col>
        </Row>
    );
  }
  
  export default ProductItemForm;