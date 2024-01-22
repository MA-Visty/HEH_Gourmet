import {useEffect, useRef, useState} from "react";
import { Form, FormGroup, Button, ButtonGroup, ButtonToolbar } from "react-bootstrap";
import axios from "axios";
import API_URL from "../../apiConfig";

export function ProductItemFilter({data, setDataFilter}) {
    const filter = useRef(null);
    const [typeFilter, setTypeFilter] = useState([]);
    const [typeSelect, setTypeSelect] = useState("");
    useEffect(() => {handleSearch();}, [typeSelect]);
    const [isCrash, setCrash] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {axios
        .get(`${API_URL}/api/typesProduct`)
        .then((response) => {
            setTypeFilter(response.data);
            setLoading(false);
        })
        .catch((error) => {
            setCrash(true);
        });
    }, []);

    const handleSearch = () => {
        if (filter.current.value !== "") {
            setTypeSelect("")
            setDataFilter(
                data.filter((product) =>
                    product.name
                        .toLowerCase()
                        .includes(filter.current.value.toLowerCase())
                )
            );
        } else if(typeSelect !== "") {
            setDataFilter(
                data.filter((product) => {
                    if(product.type !== null) {
                        product.type.typeName
                            .toLowerCase()
                            .includes(typeSelect.toLowerCase())
                        return product;
                    }
                })
            );
        } else {
            setDataFilter(data);
        }
    };

    const hangleTypeSelect = (type) => {
        filter.current.value = ""
        setTypeSelect((prevType) => (prevType === type ? "" : type));
    };

    return (
        <ButtonToolbar className="justify-content-between" aria-label="Toolbar with Button groups" style={{marginBottom: "1rem"}}>
            <ButtonGroup aria-label="First group">
                {typeFilter.map((elem) => (
                    <Button
                        variant={typeSelect === elem.typeName ? 'success' : 'outline-secondary'}
                        onClick={() => hangleTypeSelect(elem.typeName)}
                    >
                        {elem.typeName.charAt(0).toUpperCase() + elem.typeName.slice(1)}
                    </Button>
                ))}
            </ButtonGroup>

            <Form noValidate>
                <FormGroup className="d-flex justify-content-center">
                    <Form.Control ref={filter} type="text" placeholder="Search" onChange={handleSearch}/>
                </FormGroup>
            </Form>
        </ButtonToolbar>
    );
}

export default ProductItemFilter;