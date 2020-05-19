import React, { Component } from 'react';
import '../../styles/Todoitem.css'
interface Props {
    text: string;
    onToggle() : void;
    select : boolean;
}

class TodoItem extends Component<Props> {
    render() {  
        return (
            <div className={"todoitem" + (this.props.select ? ' select' : '')}
                onClick= {this.props.onToggle}
            >
            <b>
                {this.props.text}
            </b>
        </div>
        );
    }
}

export default TodoItem;