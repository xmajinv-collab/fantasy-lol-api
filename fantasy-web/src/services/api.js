const API_URL = "http://localhost:8080/api";

export async function login(username,password){

    const res = await fetch(`${API_URL}/auth/login`,{
        method:"POST",
        headers:{ "Content-Type":"application/json"},
        body:JSON.stringify({username,password})
    });

    return res.json();
}

export async function obtenerDashboard(token,ligaId){

    const res = await fetch(`${API_URL}/ligas/${ligaId}/dashboard`,{
        headers:{ Authorization:`Bearer ${token}` }
    });

    return res.json();
}

export async function obtenerMercado(token,ligaId){

    const res = await fetch(`${API_URL}/mercado/${ligaId}`,{
        headers:{ Authorization:`Bearer ${token}` }
    });

    return res.json();
}