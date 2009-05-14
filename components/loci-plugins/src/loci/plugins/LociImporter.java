//
// LociImporter.java
//

/*
LOCI Plugins for ImageJ: a collection of ImageJ plugins including the
Bio-Formats Importer, Bio-Formats Exporter, Bio-Formats Macro Extensions,
Data Browser, Stack Colorizer and Stack Slicer. Copyright (C) 2005-@year@
Melissa Linkert, Curtis Rueden and Christopher Peterson.

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package loci.plugins;

import ij.plugin.PlugIn;

import java.util.HashSet;

import loci.plugins.importer.Importer;
import loci.plugins.util.LibraryChecker;

/**
 * ImageJ plugin for reading files using the LOCI Bio-Formats package.
 * Wraps core logic in {@link loci.plugins.importer.Importer}, to avoid
 * direct references to classes in the external Bio-Formats library.
 *
 * <dl><dt><b>Source code:</b></dt>
 * <dd><a href="https://skyking.microscopy.wisc.edu/trac/java/browser/trunk/components/loci-plugins/src/loci/plugins/LociImporter.java">Trac</a>,
 * <a href="https://skyking.microscopy.wisc.edu/svn/java/trunk/components/loci-plugins/src/loci/plugins/LociImporter.java">SVN</a></dd></dl>
 *
 * @author Curtis Rueden ctrueden at wisc.edu
 * @author Melissa Linkert linkert at wisc.edu
 */
public class LociImporter implements PlugIn {

  // -- Fields --

  /**
   * Flag indicating whether last operation was successful.
   * NB: This field must be updated properly, or the plugin
   * will stop working correctly with HandleExtraFileTypes.
   */
  public boolean success;

  /**
   * Flag indicating whether last operation was canceled.
   * NB: This field must be updated properly, or the plugin
   * will stop working correctly with HandleExtraFileTypes.
   */
  public boolean canceled;

  // -- PlugIn API methods --

  /** Executes the plugin. */
  public void run(String arg) {
    canceled = false;
    success = false;
    if (!LibraryChecker.checkJava() || !LibraryChecker.checkImageJ()) return;
    HashSet missing = new HashSet();
    LibraryChecker.checkLibrary(LibraryChecker.Library.BIO_FORMATS, missing);
    LibraryChecker.checkLibrary(LibraryChecker.Library.OME_JAVA_XML, missing);
    LibraryChecker.checkLibrary(LibraryChecker.Library.FORMS, missing);
    if (!LibraryChecker.checkMissing(missing)) return;
    new Importer(this).run(arg);
  }

}
