import React, { useEffect, useState } from "react";
export default function Statistics({facade}) {
    const [breed, setbreed] = useState("")
    function fetchBreed() {
        fetch("http://localhost:8080/jpareststarter/api/searches/all")
            .then(res => res.json())
            .then(data => {
                setbreed(data)
            })
    
            setTimeout(10000000000)   
         }
    
    useEffect(() => {
        fetchBreed()
    }, [])
    console.log(breed)
    if(breed){
      return (
        <div>
            <h2>Breed Searches</h2>
            <ul>{breed.map((dog, value) =>{
                 return(

              <li>{dog.breed}: searched {dog.value} time(s)</li>
            );
            })}
            </ul>
            
               
            
        </div>
    )
}
else{
  return(
  <div>
  <h2>Breed Searches</h2>
  <p></p>   
</div>
  )}

    }
  