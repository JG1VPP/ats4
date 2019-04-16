package controllers

import javax.inject.Inject
import play.api.db.Database
import play.api.mvc.{Action, InjectedController}

import models.Post
import views._

class Board @Inject()(db: Database) extends InjectedController {
	def view = Action(Ok(html.board(Post.all(db))))
}
