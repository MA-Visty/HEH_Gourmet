import { NavLink } from "react-router-dom";
import React from "react";
import Card from 'react-bootstrap/Card';
import ProductItemForm from "./ProductItemForm";

function ProductItem({data, product}) {
	return (
		<Card style={{margin: 5}} id={product.id}>
			<NavLink to={`/product/${product.id}`}>
				<Card.Img style={{ objectFit: 'cover', height: '250px', borderBottom: '1px solid #dee2e6'}} variant="top" src={product.mainImage} />
				<Card.Body>
					<Card.Title>{product.name}</Card.Title>
				</Card.Body>
			</NavLink>
			<Card.Footer className="text-muted">
				{product.price} €
				<ProductItemForm data={data} quantity={product.quantity}/>
			</Card.Footer>
		</Card>
	);
}

export default ProductItem;