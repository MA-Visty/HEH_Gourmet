import { NavLink } from "react-router-dom";
import React, {useState} from "react";
import { Card, Row, Col } from 'react-bootstrap';
import {useAppContext} from "../../../store/AppContext";
import Add from "../../../assets/images/add.svg";

function ProductNewItem() {
	const { state } = useAppContext();
	const product = {
		ID: "newProduct",
		name: "Ajouter un produit",
		description: null,
		price: null,
		stock: 5,
		image: Add,
		categoryID: 1
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
			<NavLink to={`/product/${product.ID}`}>
				<Card.Img style={{ objectFit: 'cover', height: '250px', borderBottom: '1px solid #dee2e6'}} variant="top" src={product.image} />
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