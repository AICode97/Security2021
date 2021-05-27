import { useEffect, useState } from "react"
import { Redirect } from "react-router"
import Wrap from "../components/Wrap"
import TokenService from "../services/TokenService"
import UserService from "../services/UserService"

const tokenService = new TokenService()
const userService = new UserService()

export default function User(props) {
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState("")
    const [data, setData] = useState({})

    const loadUser = async () => {
        try {
            const user = await userService.user()
            setData(user.data)
        } catch(e) {
            setError("Please do login")
        }

        props.updateUser()

        setLoading(false)
    }

    useEffect(() => {
        if (!tokenService.hasToken()) {
            return
        }
        
        loadUser()
    }, [])

    if (error.length > 0 || !tokenService.hasToken()) {
        tokenService.deleteToken()
        return <Redirect to='/login'/>
    }

    if (loading) {
        return Wrap(<p>Loading</p>)
    }

    return Wrap(<div>
        <ul>
       
            <li>Username: { data.username }</li>
            <li>Email: { data.email }</li>
            <li>Roles
                <ul>
                    { data.roles.map(role => {
                        return <li>{ role }</li>
                    })}
                </ul>
            </li>
        </ul>
    </div>)
}

