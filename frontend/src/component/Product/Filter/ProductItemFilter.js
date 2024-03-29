import React, {useEffect, useRef, useState} from "react";
import {Form, FormGroup, Button, ButtonGroup, ButtonToolbar, Col} from "react-bootstrap";
import axios from "axios";
import API_URL from "../../../apiConfig";
import ButtonNewItemFilter from "./ButtonNewItemFilter";
import Loader from "../../Loader/Loader";
import {useAppContext} from "../../../store/AppContext";

export function ProductItemFilter({data, setDataFilter}) {
    const { state } = useAppContext();
    const filter = useRef(null);
    const [typeFilter, setTypeFilter] = useState([]);
    const [typeSelect, setTypeSelect] = useState("");
    const [loading, setLoading] = useState(true);
    useEffect(() => {handleSearch();}, [typeSelect]);

    useEffect(() => {axios
        .get(`${API_URL}/api/categories`)
        .then((response) => {
            setTypeFilter(response.data);
            setLoading(false);
        })
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
                    if(product.categoryID !== null && product.categoryID === typeSelect.ID) {
                        return product;
                    }
                })
            );
        } else {
            setDataFilter(data);
        }
    };

    const handleTypeSelect = (type) => {
        filter.current.value = ""
        setTypeSelect((prevType) => (prevType === type ? "" : type));
    };

    return (
        <ButtonToolbar className="justify-content-between" aria-label="Toolbar with Button groups" style={{marginBottom: "1rem"}}>
            <ButtonGroup aria-label="First group">
                {state.user.role === "ADMIN" ?
                    <ButtonNewItemFilter/>
                    :
                    <></>
                }
                {loading ?
                    <Loader/>
                :typeFilter.map((elem) => (
                    <Button
                        variant={typeSelect === elem ? 'success' : 'outline-secondary'}
                        onClick={() => handleTypeSelect(elem)}
                    >
                        {elem.name.charAt(0).toUpperCase() + elem.name.slice(1)}
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