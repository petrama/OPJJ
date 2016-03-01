package hr.fer.zemris.java.filechecking.fmagician.syntax;

import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.DefStatement;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.ExistsStatement;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.FailStatement;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.FileNameStatement;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.FormatStatement;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.ProgramNode;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.TerminateStatement;


/**
 * Apstraktni posjetitelj naredbi jezika <i>fmagician</i>.
 * 
 * @author Petra Marče
 */

public interface FMagicianNodeVisitor {
	
		/**
		 * Obrada naredbe "def" ({@link DefStatement}).
		 * @param stmt naredba
		 */
		public void visit(DefStatement stmt);
		/**
		 * Obrada naredbe "exist" ({@link ExistsStatement}).
		 * @param stmt naredba
		 */
		public void visit(ExistsStatement stmt);
		/**
		 * Obrada naredbe "format" ({@link FormatStatement}).
		 * @param stmt naredba
		 */
		public void visit(FormatStatement stmt);
		/**
		 * Obrada naredbe "filename" ({@link FileNameStatement}).
		 * @param stmt naredba
		 */
		public void visit(FileNameStatement stmt);
		/**
		 * Obrada naredbe "fail" ({@link FailStatement}).
		 * @param stmt naredba
		 */
		public void visit(FailStatement stmt);
		/**
		 * Obrada naredbe "terminate" ({@link TerminateStatement}).
		 * @param stmt naredba
		 */
		public void visit(TerminateStatement stmt);
		/**
		 * Obrada slijeda naredbi ({@link ProgramNode}).
		 * @param node slijed naredbi
		 */
		public void visit(ProgramNode node);
}
