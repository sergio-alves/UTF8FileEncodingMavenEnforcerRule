package ch.santosalves.maven.plugin.enforcer.rule;

import java.io.File;

import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;

import com.sun.org.apache.xerces.internal.impl.dv.xs.UnionDV;

public class FileEncodingEnforcerRule implements EnforcerRule {
	
	/**
	 * For each file : 
	 * - read all and store the name, size, last-modification,
	 */
	
	public void execute(EnforcerRuleHelper arg0) throws EnforcerRuleException {
		MavenProject project;
		try {
			project = (MavenProject)arg0.evaluate("${project}");
			File baseDir = project.getBasedir();
			
			
			
		} catch (ExpressionEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getCacheId() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isCacheable() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isResultValid(EnforcerRule arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
