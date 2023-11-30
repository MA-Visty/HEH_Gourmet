import React, { useState } from "react";
import axios from "axios";
import Container from 'react-bootstrap/Container';
import Error from "../Error/Error";
import Button from 'react-bootstrap/Button';
import Spinner from 'react-bootstrap/Spinner';
import { useParams } from "react-router-dom";
import Table from 'react-bootstrap/Table';
import Image from 'react-bootstrap/Image';


function Products() {
	const [data, setData] = useState({});
	const [isLoad, setLoad] = useState(false);
	const [isCrash, setCrash] = useState(false);
	const itemID = useParams().id;

	useState(() => 
		axios.get(`http://localhost:3000/api/product/${itemID}`)
		.then(function (reponse) {
			setData(reponse.data.product);
			setLoad(true);
		})
		.catch(function (error) {
			setCrash(true);
		})
	);

	return (
		<Container style={{marginTop: 15}}>
			{isCrash ?
				<Error />:
				isLoad ? 
					<Table striped bordered size="md">
						<tbody>
							<tr>
								<td colSpan={2} style={{textAlign: 'center', padding: '0px'}}>
									<Image style={{ objectFit: 'contain', width: '100%', height: '250px'}} src={data.mainImage} />
								</td>
							</tr>
							<tr>
								<td>Nom</td>
								<td>{data.name}</td>
							</tr>
							<tr>
								<td>Description</td>
								<td>
									<ol>{data.description.split('|').map((elem) => (
										<li>{elem}</li>
									))}
									</ol>
								</td>
							</tr>
						</tbody>
					</Table>:
					<Button variant="primary" disabled>
						<Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" />
						{' '}
						Loading...
					</Button>
			}
		</Container>
	);
}

export default Products;