
import React, { useEffect, useState } from "react";

import "../Flow.css"
export default function Flow() {
  const [flows, setFlows] = useState([]);
    const [isLoading, setIsLoading] = useState(true)
    const  [status, setStatus] =useState("")

    useEffect(() => {
        fetch("https://jsonplaceholder.typicode.com/todos")
        .then(flows => flows.json())
        .then(flows => {
            setFlows(flows)
            setIsLoading(false)
        })
    }, [])

    if (isLoading) {
        return <p>Loader</p>
    }

    return (
        <div> 
            <h1> Flows </h1>
            <div>  {status} </div>

        <div > 
            <ul>
                { flows.map(entry => {
                    return (
                        <li class="container">
                            <p class="item"> Flow Id: {entry.userId} Flow Navn: {entry.id} Beskrivelse Af Flow: {entry.title} </p>
                            
                        </li>
                        )
                }) }
            </ul>
        </div>
        </div>
    )
  }