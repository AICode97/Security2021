import { useEffect, useState } from "react"
import UserService from "../services/UserService";
import FlowService from "../services/FlowService"
import "../admin.css"
import { Redirect } from "react-router";

const initialData = {
    name: '',
    description: ''
}

const userService = new UserService()
const flowService = new FlowService()

export default function Admin(props) {
    const [isAdmin, setIsAdmin] = useState(false)
    const [isLoading, setIsLoading] = useState(true)
    const [data, setData] = useState(initialData)
    const [error, setError] = useState("")
    const [users, setUsers] = useState([])

    const determineRole = async() => {
        const admin = await userService.isAdmin()
        setIsAdmin(admin)

        if (!admin) {
            setError('Not allowed')
            setIsLoading(false)
            return
        }

        props.updateUser()

        fetchAllUsers()
    }

    const fetchAllUsers = async() => {
        const response = await userService.all()

        setUsers(response.data)
        setIsLoading(false)
    }

    useEffect(() => {
        determineRole()
    }, [])

    if (isLoading) {
        return <p>LOADER</p>
    }

    if (!isAdmin) {
        return <Redirect to='/'/>
    }

    const setFormData = (key) => {
        return (event) => {
            setData({
                ...data,
                [key]: event.currentTarget.value,
            })
        }
    }
    
    const onButtonClick = async () => {
        try {
            console.log(data)
            
            await flowService.makeFlow(data)
        } catch (_) {
            setError("Could not create flow")
        }
    }

    return (
        <div> 


            <h1>Admin Page</h1>
            
            <div className="split left">
                <div className="userHeader">All Users</div>
                    <div> { users.map((user, index) => <p key={ index }>{user}</p>)} </div> 
                </div>
        

         
            <div>
                <div className="split right">
                    <label>
                        <p>Name Of Flow:</p>
                        <input value={ data.name } onChange={ setFormData("name") } type="text" className="nameOfFlow"/>
                    </label>

                    <label>
                        <p>Description Of Flow:</p>
                        <textarea className="hej" value={ data.description } onChange={ setFormData("description") } type="text" className="descriptionOfFlow"/>
                    </label>

                    <button onClick={ onButtonClick }>
                        Make Flow
                    </button>
                </div>
            </div>
        </div>
    )
}