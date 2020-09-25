package models

import java.nio.file.{Files, Path, Paths}
import play.api.data.{Form, Forms}
import play.api.libs.Files.TemporaryFile
import play.libs.mailer.{Email, MailerClient}
import qxsl.extra.field.Call
import qxsl.ruler.{Contest, RuleKit, Section}
import scala.util.Try

case class Part(sect: String, city: String) {
	def code = rule.getCode
	def rule = Sections.find(sect)
	def game(call: String, file: String) = {
		val score = new TableLoader(file, sect=sect).score
		Game(call, sect, city, score=score.score, total=score.total, file=file)
	}
}

case class Post(team: Team, parts: Seq[Option[Part]]) {
	def games(file: String): Seq[Game] = {
		val parts = this.parts.filter(_.nonEmpty).map(_.get)
		val rules = parts.map(_.rule)
		val place = parts.map(_.city).distinct.mkString(" ")
		val joint = Part(Sections.joint(rules).getName, place)
		return (parts :+ joint).map(_.game(team.call, file))
	}
}

object PostForm extends Form[Post](Forms.mapping(
	"team" -> Forms.mapping(
		"call" -> Forms.nonEmptyText.verifying(s => Try(new Call(s)).isSuccess),
		"name" -> Forms.nonEmptyText,
		"addr" -> Forms.nonEmptyText,
		"mail" -> Forms.email,
		"comm" -> Forms.text,
	)(Team.apply)(Team.unapply),
	"parts" -> Forms.seq(Forms.optional(Forms.mapping(
		"sect" -> Forms.text,
		"city" -> Forms.text
	)(Part.apply)(Part.unapply).verifying(Sections.valid _)))
)(Post.apply)(Post.unapply), Map.empty, Nil, None)