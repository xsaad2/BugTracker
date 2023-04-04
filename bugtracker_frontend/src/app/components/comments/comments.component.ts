import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Ticket } from 'src/app/models/ticket';
import { CommentService } from 'src/app/services/comment.service';
import { TicketService } from 'src/app/services/ticket.service';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css'],
})
export class CommentsComponent implements OnInit {
  @Input() ticket!: Ticket;
  ticketComments!: Comment[];
  commentForm!: FormGroup;

  constructor(
    private ticketService: TicketService,
    private commentService: CommentService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.commentForm = this.formBuilder.group({
      content: ['', Validators.required],
    });

    this.commentService.subscribeToCommentsChange((): void => {
      this.ticketService
        .getTicketById(this.ticket.id)
        .subscribe((res) => (this.ticket = res));
    });
  }

  onAddComment() {
    const commentDto: any = {
      content: this.commentForm.value.content,
      commentedTicketId: this.ticket.id,
    };
    console.log(
      'model: ' + commentDto.content + ' ' + commentDto.commentedTicketId
    );
    this.commentService.createComment(commentDto).subscribe(
      (response) => {
        this.dialog.closeAll();
      },
      (error) => {
        this.dialog.closeAll();
        this._snackBar.open('Comment creation failed', 'OK', {
          duration: 5000,
          panelClass: ['failure-snackbar'],
        });
      }
    );
    this.commentForm.value.content = '';
    this.commentForm.reset();
  }
  deleteComment(commentId: string) {
    this.commentService.deleteCommentById(commentId).subscribe();
  }
}
