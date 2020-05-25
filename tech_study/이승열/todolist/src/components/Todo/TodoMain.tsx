import React, { Component } from 'react';
import '../../styles/TodoMain.css';
import TodoList from './TodoList'
import Arrow from './Arrow'
interface Props {

}

interface TodoItemState {
    id: number;
    text: string;
    status:number;
    select : boolean
}

interface State {
    input: string;
    Items: TodoItemState[];
}

class TodoMain extends Component<Props, State> {
    nextTodoId: number = 0;

    state:State = {
        input: '',
        Items: [],
    };

    onSubmit = (e: React.FormEvent<HTMLFormElement>):void => {
        e.preventDefault();
        const { Items, input } = this.state;
        if (input){
            const newItem:TodoItemState = { id: this.nextTodoId++, text: input, status : 0, select:false};
            const nextTodoItems:TodoItemState[] = Items.concat(newItem);
            this.setState({
                input: '',
                Items: nextTodoItems
            });
        } else {
            alert('할 일을 입력하세요')
        }
    }
    onChange = (e: React.FormEvent<HTMLInputElement>): void => {
        const { value } = e.currentTarget;
        this.setState({
            input: value
        });
    }
    move = (a : number,b : number): void => {
        console.log(a ,'->',b)
        const { Items } = this.state;
        var selected = Items.filter(item => (item.status === a && item.select))
        for (var i in selected){
            selected[i].status = b
            selected[i].select = false
        }
    }

    render() {
        const { onSubmit, onChange, move} = this;
        const { input, Items } = this.state;
        
        const firstItems = Items.filter(item => item.status === 0)
        const DoingItems = Items.filter(item => item.status === 1)
        const DoneItems = Items.filter(item => item.status === 2)

        return (
            <div className="ctr80">
                <h1>어서오쇼~
                </h1>
                <form onSubmit={onSubmit}>
                <input onChange={onChange} value={input} />
                <button type="submit">추가하기</button>
                </form>
                <div className="lists">
                    <TodoList
                    key="1"
                    title="Start"
                    items={firstItems}
                    />
                    <Arrow goPrev={()=>move(1,0)}
                            goNext={()=>move(0,1)}
                            />
                    <TodoList 
                    key="2"
                    title="Doing"
                    items={DoingItems}
                    />
                    <Arrow goPrev={()=>move(2,1)}
                            goNext={()=>move(1,2)}
                    />
                    <TodoList 
                    key="3"
                    title="Done"
                    items={DoneItems}
                    />
                </div>
            </div>
        );
    }
}

export default TodoMain;