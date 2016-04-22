import play.api.libs.functional.syntax._
import play.api.libs.json.{Writes, _}

val a = "a"
Json.toJson("s")

case class A(a:String,b:String)

object A {
  implicit val billingSummaryReads: Reads[A] = (
    (__ \ "a").read[String] ~ (__ \ "b").read[String]
    ) (A.apply _)

  implicit val billingSummaryWrites: Writes[A] = (
    (__ \ "a").write[String] ~ (__ \ "b").write[String]
    ) (unlift(A.unapply))
}

Json.toJson(A("a","b"))

case class B(a:String)
implicit val aWrites: Writes[B] = Writes(new ((B) => JsValue) {
  def apply(x: B): JsValue = {
    Json.toJson(JsObject(Map("name" -> JsString(x.a))))
  }
})

implicit val bWrites: Writes[String] = Writes(x => Json.toJson(JsObject(Map("v" -> JsString(x)))))

Json.toJson(B("a")).toString()

