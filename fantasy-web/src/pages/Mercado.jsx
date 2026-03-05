import { useEffect,useState } from "react";
import { obtenerMercado } from "../services/api";

export default function Mercado(){

    const [jugadores,setJugadores] = useState([]);

    useEffect(()=>{

        const token = localStorage.getItem("token");

        obtenerMercado(token,1)
        .then(res => setJugadores(res));

    },[]);

    return(

        <div>

            <h1>Mercado</h1>

            {jugadores.map(j =>(

                <div key={j.id}>

                    {j.nombre} - {j.precio}

                </div>

            ))}

        </div>

    );
}