import { Component, input, InputSignal, output } from '@angular/core';
import { ChatResponse, UserResponse } from '../../services/models';
import { DatePipe } from '@angular/common';
import { UserService } from '../../services/services';

@Component({
  selector: 'app-chat-list',
  imports: [ DatePipe ],
  templateUrl: './chat-list.component.html',
  styleUrl: './chat-list.component.scss'
})
export class ChatListComponent {

  chats: InputSignal<ChatResponse[]> = input<ChatResponse[]>([]);
  searchNewContact: boolean = false;
  contacts: Array<UserResponse> = [];
  chatSelected = output<ChatResponse>();

  constructor(private userService: UserService) {

  }

  searchContact(){
    this.userService.getAllUsers()
    .subscribe({
      next: (users) => {
        this.contacts = users;
        this.searchNewContact = true
      }
    })
  }

  chatClicked(chat: ChatResponse){
    this.chatSelected.emit(chat);
  }

  wrapMessage(lastMessage: string | undefined): string{
    if (lastMessage && lastMessage.length <= 20){
      return lastMessage;
    }
    return lastMessage?.substring(0, 17) + '...';
  }

  selectContact(contact: UserResponse){
    
  }
}
