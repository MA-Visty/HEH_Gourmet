import React, { useContext } from "react";
import InputGroup from 'react-bootstrap/InputGroup';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
//import useAppContext from '../../store/AppContext';

function ProductItemForm({data, quantity}) {
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

	return (<></>);

	/*
	return (
		<InputGroup className="mb-3">
			<Form.Control type="number" min="0" max={quantity} aria-label="Recipient's username" aria-describedby="basic-addon2" />
			<Button variant="primary" id="button-addon2" onClick={AddCart}>Add</Button>
		</InputGroup>
		
	);

	 */
  }
  
  export default ProductItemForm;