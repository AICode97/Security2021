export default function Wrap(component) {
    return (
        <div className="layout">
            <div className="box">
                { component }
            </div>
        </div>
    )
}