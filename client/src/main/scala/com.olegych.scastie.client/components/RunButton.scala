package com.olegych.scastie.client
package components

import com.olegych.scastie.client.components.editor.EditorOptions
import japgolly.scalajs.react._
import vdom.all._
import extra._

final case class RunButton(isRunning: Boolean, isStatusOk: Boolean, save: Reusable[Callback], setView: View ~=> Callback, embedded: Boolean) {
  @inline def render: VdomElement = RunButton.component(this)
}

object RunButton {

  implicit val reusability: Reusability[RunButton] =
    Reusability.derive[RunButton]

  def render(props: RunButton): VdomElement = {
    if (!props.isRunning) {
      val runTitle =
        if (props.isStatusOk)
          s"Run (${EditorOptions.Keys.saveOrUpdate})"
        else
          s"Run (${EditorOptions.Keys.saveOrUpdate}) - warning: unknown status"

      li(onClick --> props.save, role := "button", title := runTitle, cls := "btn run-button")(
        i(cls := "fa fa-play"),
        span("Run")
      )
    } else {
      li(onClick --> props.setView(View.Editor), title := "Running your Code...", cls := "btn run-button")(
        i(cls := "fa fa-spinner fa-spin"),
        span("Running")
      )
    }
  }

  private val component =
    ScalaComponent
      .builder[RunButton]("RunButton")
      .render_P(render)
      .configure(Reusability.shouldComponentUpdate)
      .build
}
