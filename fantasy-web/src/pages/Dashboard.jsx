import { useEffect,useState } from "react";
import { obtenerDashboard } from "../services/api";

export default function Dashboard(){

    const [dashboard,setDashboard] = useState(null);

    useEffect(()=>{

        const token = localStorage.getItem("token");

        obtenerDashboard(token,1)
        .then(res => setDashboard(res.data));

    },[]);

    if(!dashboard) return <p>Cargando...</p>;

    return(

        <div>

            <h1>{dashboard.nombre}</h1>

            <h2>Ranking</h2>

            <ul>

                {dashboard.ranking.map(e =>(

                    <li key={e.posicion}>
                        {e.nombreEquipo} - {e.puntosTotales}
                    </li>

                ))}

            </ul>

        </div>

    );
}