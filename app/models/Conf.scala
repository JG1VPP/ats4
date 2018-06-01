package models

import java.io.File
import java.time.LocalDate
import qxsl.field.City
import qxsl.ruler.Contest
import scala.collection.JavaConversions._

object Conf {
	val name = "ALLJA1"
	val what = "ALLJA1コンテストは関東地方を中心としたアマチュア無線の全国大会です"
	val host = "東京大学アマチュア無線クラブ"
	val mail = "allja1@ja1zlo.u-tokyo.org"
	val site = "ja1zlo.u-tokyo.org"
	val rule = "ja1zlo.u-tokyo.org/allja1/%02drule.html".format(time)
	val ats3 = "allja1.org"
	val subj = "ALLJA1 Entry Acceptance"
	val repl = "allja1@ja1zlo.u-tokyo.org"
	val demo = true
	def time = LocalDate.of(1988,5,1).until(LocalDate.now()).getYears()
	def isOK = LocalDate.now().getMonthValue() == 6 || demo
	def save = new File(System.getProperty("user.home"), "ats4.rcvd")
	val test = Contest.forName("allja1.lisp")
	def sects = test.toList
	def sectsAM = sects.map(_.getName).filter(_.matches(""".*(1\.9|3\.5|7).*"""))
	def sectsPM = sects.map(_.getName).filter(_.matches(""".*(14|21|28|50).*"""))
	def sectsRC = sects.map(_.getName).filter(_.contains("社団"))
	def sectsAllBands = (sectsAM ++ sectsPM).filter(_.contains("/"))
	def sect(name: String) = sects.filter(_.getName == name).head
	def citys = City.getAvailableCodes.map(new City(_)).filter(c=>c.getPrefName!=c.getCityName)
	def prefs = City.getAvailableCodes.map(new City(_)).filter(c=>c.getPrefName==c.getCityName)
	val area1 = Seq("東京都", "神奈川県", "埼玉県", "千葉県", "群馬県", "茨城県", "栃木県", "山梨県")
	def inner = citys.filter(c=>area1.contains(c.getPrefName))
	def outer = prefs.filterNot(c=>area1.contains(c.getPrefName))
}
