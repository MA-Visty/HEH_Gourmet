import { NavLink } from "react-router-dom";
import React, {useState} from "react";
import { Card, Row, Col } from 'react-bootstrap';
import {useAppContext} from "../../store/AppContext";
import Edit from "../../assets/images/edit.svg";

function ProductNewItem() {
	const { state } = useAppContext();
	const product = {
		id: "newProduct",
		type: null,
		name: "Ajouter un produit",
		price: null,
		description: null,
		imageName: null,
		imageId: null,
		imageUrl: Edit,
		ingredients: []
	};

	return (
		<Card
			style={{margin: 5}}
			id={product.id}
			onDragStart={(e) => {e.preventDefault();}}
			style={{
				MozUserSelect: "none",
				WebkitUserSelect: "none",
				msUserSelect: "none"
		}}>
			<NavLink to={`/product/${product.id}`}>
				<Card.Img style={{ objectFit: 'cover', height: '250px', borderBottom: '1px solid #dee2e6'}} variant="top" src={product.imageUrl} />
				<Card.Body>
					<Row className="justify-content-between" style={{color: "black", textDecoration: "none"}}>
						<Col >
							<Card.Title style={{textAlign: "center", marginBottom: "0"}}>{product.name.charAt(0).toUpperCase() + product.name.slice(1)}</Card.Title>
						</Col>
					</Row>
				</Card.Body>
			</NavLink>
		</Card>
	);
}

export default ProductNewItem;