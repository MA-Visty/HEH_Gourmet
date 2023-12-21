import React, {useState} from "react";
import {Badge} from 'react-bootstrap';
import StarImage from "../../assets/images/star.svg"
import StarCompletImage from "../../assets/images/starComplet.svg"
import {useAppContext, useDispatchContext} from "../../store/AppContext";

function ProductItemFavorite({product}) {
    const { state } = useAppContext();
    const { dispatch } = useDispatchContext();
    const [isEnter, setEnter] = useState(false);

    const handleClickFav = (event) => {
        if(state.favorite.includes(product.id)) {
            dispatch({
                type: "removefav",
                product: product.id
            });
        } else {
            dispatch({
                type: "addfav",
                product: product.id
            });
        }
    }

    return (
        <Badge bg="" pill style={{position: 'absolute', top: 5, right: 5, zIndex: 1}}>
            <img
                src={state.favorite.includes(product.id) ? StarCompletImage : StarImage}
                alt="error"
                width={35}
                onClick={handleClickFav}
                onMouseEnter={() => setEnter(true)}
                onMouseLeave={() => setEnter(false)}
                style={{
                    cursor: 'pointer',
                    transition: 'transform 0.1s ease-in-out',
                    transformOrigin: 'center',
                    transform: isEnter ? 'scale(1.2)' : 'scale(1)'
                }}/>
        </Badge>
    );
}

export default ProductItemFavorite;