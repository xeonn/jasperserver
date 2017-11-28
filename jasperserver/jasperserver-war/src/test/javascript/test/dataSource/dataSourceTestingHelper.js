/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @author: Dima Gorbenko <dgorbenko@jaspersoft.com>
 * @version: $Id: dataSourceTestingHelper.js 41121 2014-01-29 16:26:00Z dgorbenko $
 */

define(function(require) {
	"use strict";

	var
		$ = require("jquery"),
		_ = require("underscore"),
		selectFromRepository = {block: null, parent: null},
		dataSourceTemplate = require("text!templates/dataSource.htm"),
		treeTemplate = require("text!templates/tree.htm");

	return {
		beforeEach: function() {
			// before installing template, we need to remove the '#selectFromRepository' element and then get it back
			selectFromRepository.block = $("#selectFromRepository");
			selectFromRepository.parent = selectFromRepository.block.parent();
			selectFromRepository.block.detach();

			// install template
			setTemplates(dataSourceTemplate, treeTemplate);
		},

		afterEach: function() {
			// get back the "#selectFromRepository" element
			selectFromRepository.parent.append(selectFromRepository.block);
		}
	}
});