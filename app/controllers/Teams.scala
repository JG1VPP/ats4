package controllers

import javax.inject.{Inject,Singleton}
import play.api.db.Database
import play.api.mvc.{Action,InjectedController}

@Singleton class Teams extends InjectedController {
	@Inject implicit var db: Database = null
	def view = Action(Ok(views.html.pages.teams()))
}