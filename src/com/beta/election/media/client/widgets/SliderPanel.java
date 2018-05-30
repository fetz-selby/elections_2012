package com.beta.election.media.client.widgets;

import static com.google.gwt.query.client.GQuery.$;

import com.beta.election.media.client.utils.EffectConstants;
import com.beta.election.media.client.widgets.handlers.HasSlidingHandler;
import com.beta.election.media.client.widgets.handlers.SlidingHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class SliderPanel extends SimplePanel implements HasSlidingHandler {
	private String direction;
	private Widget widget;
	private SlidingHandler handler;
	private int timer = 1000;

	public SliderPanel() {
	}

	public SliderPanel(String direction, int timer, Widget widget) {
		this.direction = direction;
		this.widget = widget;
		this.timer = timer;
		setWidget(widget);
	}

	public void setChildWidget(Widget child) {
		this.widget = child;
		setWidget(child);
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public void load() {
		doSliding();
	}

	private void doSliding() {
		if (widget == null) {
			widget = getWidget();
		}

		if (direction.equals(EffectConstants.SLIDEDOWN)) {
			$(widget).hide().slideDown(timer, new Function() {
				public void f(Widget w) {
					if (handler != null) {
						handler.onSlidingComplete(w);
					}
				}
			});

		} else if (direction.equals(EffectConstants.SLIDEUP)) {
			$(widget).hide().slideUp(timer, new Function() {
				public void f(Widget w) {
					if (handler != null) {
						handler.onSlidingComplete(w);
					}
				}
			});
		}
	}

	@Override
	public void setSlidingHandler(SlidingHandler handler) {
		this.handler = handler;
	}
}
