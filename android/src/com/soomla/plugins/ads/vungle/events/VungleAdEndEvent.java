/*
 * Copyright (C) 2012-2014 Soomla Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.soomla.plugins.ads.vungle.events;

import com.soomla.events.SoomlaEvent;

/**
 * This event is fired on when Vungle Ad ends.
 */
public class VungleAdEndEvent extends SoomlaEvent {
    public VungleAdEndEvent() {
        this(null);
    }

    public VungleAdEndEvent(Object sender) {
        super(sender);
    }
}
