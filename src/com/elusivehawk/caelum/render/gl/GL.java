
package com.elusivehawk.caelum.render.gl;

/**
 * 
 * A wrapper for modern OpenGL functions.
 * <p>
 * NOTICE: By "modern", I mean only the functions found here: http://www.opengl.org/sdk/docs/man/
 * <p>
 * @author Elusivehawk
 */
/*public final class GL
{
	private GL(){}
	
	//GL A
	
	public static void glActiveShaderProgram(int pipeline, int program)
	{
		GL41.glActiveShaderProgram(pipeline, program);
		
	}
	
	public static void glActiveTexture(int texture)
	{
		GL13.glActiveTexture(texture);
		
	}
	
	public static void glActiveTexture(ITexture texture)
	{
		glActiveTexture(texture == null ? 0 : texture.getTexture(true));
		
	}
	
	public static void glAttachShader(int program, int shader)
	{
		GL20.glAttachShader(program, shader);
		
	}
	
	//GL B
	
	public static void glBeginConditionalRender(int id, int mode)
	{
		GL30.glBeginConditionalRender(id, mode);
		
	}
	
	public static void glBeginQuery(int target, int id)
	{
		GL15.glBeginQuery(target, id);
		
	}
	
	public static void glBeginQueryIndexed(int target, int index, int id)
	{
		GL40.glBeginQueryIndexed(target, index, id);
		
	}
	
	public static void glBeginTransformFeedback(int primitiveMode)
	{
		GL30.glBeginTransformFeedback(primitiveMode);
		
	}
	
	public static void glBindAttribLocation(int program, int index, ByteBuffer name)
	{
		GL20.glBindAttribLocation(program, index, name);
		
	}
	
	public static void glBindAttribLocation(int program, int index, CharSequence name)
	{
		GL20.glBindAttribLocation(program, index, name);
		
	}
	
	public static void glBindBuffer(int target, int buffer)
	{
		GL15.glBindBuffer(target, buffer);
		
	}
	
	public static void glBindBuffer(VertexBufferObject vbo)
	{
		glBindBuffer(vbo.t, vbo.id);
		
	}
	
	public static void glBindBufferBase(int target, int index, int buffer)
	{
		GL30.glBindBufferBase(target, index, buffer);
		
	}
	
	public static void glBindBufferRange(int target, int index, int buffer, long offset, long size)
	{
		GL30.glBindBufferRange(target, index, buffer, offset, size);
		
	}
	
	public static void glBindBuffersBase(int target, int first, int count, IntBuffer buffers)
	{
		GL44.glBindBuffersBase(target, first, count, buffers);
		
	}
	
	public static void glBindBuffersRange(int target, int first, int count, IntBuffer buffers, PointerBuffer offsets, PointerBuffer sizes)
	{
		GL44.glBindBuffersRange(target, first, count, buffers, offsets, sizes);
		
	}
	
	public static void glBindFragDataLocation(int program, int colorNumber, ByteBuffer name)
	{
		GL30.glBindFragDataLocation(program, colorNumber, name);
		
	}
	
	public static void glBindFragDataLocation(int program, int colorNumber, CharSequence name)
	{
		GL30.glBindFragDataLocation(program, colorNumber, name);
		
	}
	
	public static void glBindFragDataLocationIndexed(int program, int colorNumber, int index, ByteBuffer name)
	{
		GL33.glBindFragDataLocationIndexed(program, colorNumber, index, name);
		
	}
	
	public static void glBindFragDataLocationIndexed(int program, int colorNumber, int index, CharSequence name)
	{
		GL33.glBindFragDataLocationIndexed(program, colorNumber, index, name);
		
	}
	
	public static void glBindFramebuffer(int target, int framebuffer)
	{
		GL30.glBindFramebuffer(target, framebuffer);
		
	}
	
	public static void glBindImageTexture(int unit, int texture, int level, boolean layered, int layer, int access, int format)
	{
		GL42.glBindImageTexture(unit, texture, level, layered, layer, access, format);
		
	}
	
	public static void glBindImageTextures(int first, int count, IntBuffer textures)
	{
		GL44.glBindImageTextures(first, count, textures);
		
	}
	
	public static void glBindProgramPipeline(int pipeline)
	{
		GL41.glBindProgramPipeline(pipeline);
		
	}
	
	public static void glBindRenderbuffer(int target, int renderbuffer)
	{
		GL30.glBindRenderbuffer(target, renderbuffer);
		
	}
	
	public static void glBindSampler(int unit, int sampler)
	{
		GL33.glBindSampler(unit, sampler);
		
	}
	
	public static void glBindSamplers(int first, int count, IntBuffer samplers)
	{
		GL44.glBindSamplers(first, count, samplers);
		
	}
	
	public static void glBindTexture(int target, int texture)
	{
		GL11.glBindTexture(target, texture);
		
	}
	
	public static void glBindTexture(int target, ITexture texture)
	{
		GL11.glBindTexture(target, texture == null ? 0 : texture.getTexture(true));
		
	}
	
	public static void glBindTextures(int first, int count, IntBuffer textures)
	{
		GL44.glBindTextures(first, count, textures);
		
	}
	
	public static void glBindTransformFeedback(int target, int id)
	{
		GL40.glBindTransformFeedback(target, id);
		
	}
	
	public static void glBindVertexArray(int array)
	{
		GL30.glBindVertexArray(array);
		
	}
	
	public static void glBindVertexBuffer(int bindingindex, int buffer, long offset, int stride)
	{
		GL43.glBindVertexBuffer(bindingindex, buffer, offset, stride);
		
	}
	
	public static void glBindVertexBuffers(int first, int count, IntBuffer buffers, PointerBuffer offsets, IntBuffer strides)
	{
		GL44.glBindVertexBuffers(first, count, buffers, offsets, strides);
		
	}
	
	public static void glBlendColor(float red, float green, float blue, float alpha)
	{
		GL14.glBlendColor(red, green, blue, alpha);
		
	}
	
	public static void glBlendEquation(int mode)
	{
		GL14.glBlendEquation(mode);
		
	}
	
	public static void glBlendEquationi(int buf, int mode)
	{
		GL40.glBlendEquationi(buf, mode);
		
	}
	
	public static void glBlendEquationSeparate(int modeRGB, int modeAlpha)
	{
		GL20.glBlendEquationSeparate(modeRGB, modeAlpha);
		
	}
	
	public static void glBlendEquationSeparatei(int buf, int modeRGB, int modeAlpha)
	{
		GL40.glBlendEquationSeparatei(buf, modeRGB, modeAlpha);
		
	}
	
	public static void glBlendFunc(int sfactor, int dfactor)
	{
		GL11.glBlendFunc(sfactor, dfactor);
		
	}
	
	public static void glBlendFunci(int buf, int src, int dst)
	{
		GL40.glBlendFunci(buf, src, dst);
		
	}
	
	public static void glBlendFuncSeparate(int sfactorRGB, int dfactorRGB, int sfactorAlpha, int dfactorAlpha)
	{
		GL14.glBlendFuncSeparate(sfactorRGB, dfactorRGB, sfactorAlpha, dfactorAlpha);
		
	}
	
	public static void glBlendFuncSeparatei(int buf, int srcRGB, int dstRGB, int srcAlpha, int dstAlpha)
	{
		GL40.glBlendFuncSeparatei(buf, srcRGB, dstRGB, srcAlpha, dstAlpha);
		
	}
	
	public static void glBlitFramebuffer(int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter)
	{
		GL30.glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
		
	}
	
	public static void glBufferData(int target, ByteBuffer data, int usage)
	{
		GL15.glBufferData(target, data, usage);
		
	}
	
	public static void glBufferData(int target, DoubleBuffer data, int usage)
	{
		GL15.glBufferData(target, data, usage);
		
	}
	
	public static void glBufferData(int target, FloatBuffer data, int usage)
	{
		GL15.glBufferData(target, data, usage);
		
	}
	
	public static void glBufferData(int target, IntBuffer data, int usage)
	{
		GL15.glBufferData(target, data, usage);
		
	}
	
	public static void glBufferData(int target, long data_size, int usage)
	{
		GL15.glBufferData(target, data_size, usage);
		
	}
	
	public static void glBufferData(int target, ShortBuffer data, int usage)
	{
		GL15.glBufferData(target, data, usage);
		
	}
	
	public static void glBufferStorage(int target, ByteBuffer data, int flags)
	{
		GL44.glBufferStorage(target, data, flags);
		
	}
	
	public static void glBufferStorage(int target, DoubleBuffer data, int flags)
	{
		GL44.glBufferStorage(target, data, flags);
		
	}
	
	public static void glBufferStorage(int target, FloatBuffer data, int flags)
	{
		GL44.glBufferStorage(target, data, flags);
		
	}
	
	public static void glBufferStorage(int target, IntBuffer data, int flags)
	{
		GL44.glBufferStorage(target, data, flags);
		
	}
	
	public static void glBufferStorage(int target, long size, int flags)
	{
		GL44.glBufferStorage(target, size, flags);
		
	}
	
	public static void glBufferStorage(int target, LongBuffer data, int flags)
	{
		GL44.glBufferStorage(target, data, flags);
		
	}
	
	public static void glBufferStorage(int target, ShortBuffer data, int flags)
	{
		GL44.glBufferStorage(target, data, flags);
		
	}
	
	public static void glBufferSubData(int target, long offset, ByteBuffer data)
	{
		GL15.glBufferSubData(target, offset, data);
		
	}
	
	public static void glBufferSubData(int target, long offset, DoubleBuffer data)
	{
		GL15.glBufferSubData(target, offset, data);
		
	}
	
	public static void glBufferSubData(int target, long offset, FloatBuffer data)
	{
		GL15.glBufferSubData(target, offset, data);
		
	}
	
	public static void glBufferSubData(int target, long offset, IntBuffer data)
	{
		GL15.glBufferSubData(target, offset, data);
		
	}
	
	public static void glBufferSubData(int target, long offset, ShortBuffer data)
	{
		GL15.glBufferSubData(target, offset, data);
		
	}
	
	//GL C
	
	public static int glCheckFramebufferStatus(int target)
	{
		return GL30.glCheckFramebufferStatus(target);
	}
	
	public static void glClampColor(int target, int clamp)
	{
		GL30.glClampColor(target, clamp);
		
	}
	
	public static void glClear(int mask)
	{
		GL11.glClear(mask);
		
	}
	
	public static void glClearBuffer(int buffer, int drawbuffer, FloatBuffer value)
	{
		GL30.glClearBuffer(buffer, drawbuffer, value);
		
	}
	
	public static void glClearBuffer(int buffer, int drawbuffer, IntBuffer value)
	{
		GL30.glClearBuffer(buffer, drawbuffer, value);
		
	}
	
	public static void glClearBufferData(int target, int internalformat, int format, int type, ByteBuffer data)
	{
		GL43.glClearBufferData(target, internalformat, format, type, data);
		
	}
	
	public static void glClearBufferfi(int buffer, int drawbuffer, float depth, int stencil)
	{
		GL30.glClearBufferfi(buffer, drawbuffer, depth, stencil);
		
	}
	
	public static void glClearBufferSubData(int target, int internalformat, long offset, int format, int type, ByteBuffer data)
	{
		GL43.glClearBufferSubData(target, internalformat, offset, format, type, data);
		
	}
	
	public static void glClearBufferu(int buffer, int drawbuffer, IntBuffer value)
	{
		GL30.glClearBufferu(buffer, drawbuffer, value);
		
	}
	
	public static void glClearColor(float red, float green, float blue, float alpha)
	{
		GL11.glClearColor(red, green, blue, alpha);
		
	}
	
	public static void glClearDepth(double depth)
	{
		GL11.glClearDepth(depth);
		
	}
	
	public static void glClearDepthf(float d)
	{
		GL41.glClearDepthf(d);
		
	}
	
	public static void glClearStencil(int s)
	{
		GL11.glClearStencil(s);
		
	}
	
	public static void glClearTexImage(int texture, int level, int format, int type, ByteBuffer data)
	{
		GL44.glClearTexImage(texture, level, format, type, data);
		
	}
	
	public static void glClearTexImage(int texture, int level, int format, int type, DoubleBuffer data)
	{
		GL44.glClearTexImage(texture, level, format, type, data);
		
	}
	
	public static void glClearTexImage(int texture, int level, int format, int type, FloatBuffer data)
	{
		GL44.glClearTexImage(texture, level, format, type, data);
		
	}
	
	public static void glClearTexImage(int texture, int level, int format, int type, IntBuffer data)
	{
		GL44.glClearTexImage(texture, level, format, type, data);
		
	}
	
	public static void glClearTexImage(int texture, int level, int format, int type, LongBuffer data)
	{
		GL44.glClearTexImage(texture, level, format, type, data);
		
	}
	
	public static void glClearTexImage(int texture, int level, int format, int type, ShortBuffer data)
	{
		GL44.glClearTexImage(texture, level, format, type, data);
		
	}
	
	public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ByteBuffer data)
	{
		GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
		
	}
	
	public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, DoubleBuffer data)
	{
		GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
		
	}
	
	public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, FloatBuffer data)
	{
		GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
		
	}
	
	public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, IntBuffer data)
	{
		GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
		
	}
	
	public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, LongBuffer data)
	{
		GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
		
	}
	
	public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ShortBuffer data)
	{
		GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
		
	}
	
	public static int glClientWaitSync(GLSync sync, int flags, long timeout)
	{
		return GL32.glClientWaitSync(sync, flags, timeout);
	}
	
	public static void glColorMask(boolean red, boolean green, boolean blue, boolean alpha)
	{
		GL11.glColorMask(red, green, blue, alpha);
		
	}
	
	public static void glColorMaski(int buf, boolean r, boolean g, boolean b, boolean a)
	{
		GL30.glColorMaski(buf, r, g, b, a);
		
	}
	
	public static void glCompileShader(int shader)
	{
		GL20.glCompileShader(shader);
		
	}
	
	public static void glCompressedTexImage1D(int target, int level, int internalformat, int width, int border, ByteBuffer data)
	{
		GL13.glCompressedTexImage1D(target, level, internalformat, width, border, data);
		
	}
	
	public static void glCompressedTexImage1D(int target, int level, int internalformat, int width, int border, int data_imageSize, long data_buffer_offset)
	{
		GL13.glCompressedTexImage1D(target, level, internalformat, width, border, data_imageSize, data_buffer_offset);
		
	}
	
	public static void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, ByteBuffer data)
	{
		GL13.glCompressedTexImage2D(target, level, internalformat, width, height, border, data);
		
	}
	
	public static void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int data_imageSize, long data_buffer_offset)
	{
		GL13.glCompressedTexImage2D(target, level, internalformat, width, height, border, data_imageSize, data_buffer_offset);
		
	}
	
	public static void glCompressedTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, ByteBuffer data)
	{
		GL13.glCompressedTexImage3D(target, level, internalformat, width, height, depth, border, data);
		
	}
	
	public static void glCompressedTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int data_imageSize, long data_buffer_offset)
	{
		GL13.glCompressedTexImage3D(target, level, internalformat, width, height, depth, border, data_imageSize, data_buffer_offset);
		
	}
	
	public static void glCompressedTexSubImage1D(int target, int level, int xoffset, int width, int format, ByteBuffer data)
	{
		GL13.glCompressedTexSubImage1D(target, level, xoffset, width, format, data);
		
	}
	
	public static void glCompressedTexSubImage1D(int target, int level, int xoffset, int width, int format, int data_imageSize, long data_buffer_offset)
	{
		GL13.glCompressedTexSubImage1D(target, level, xoffset, width, format, data_imageSize, data_buffer_offset);
		
	}
	
	public static void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, ByteBuffer data)
	{
		GL13.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width, height, format, data);
		
	}
	
	public static void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int data_imageSize, long data_buffer_offset)
	{
		GL13.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width, height, format, data_imageSize, data_buffer_offset);
		
	}
	
	public static void glCompressedTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, ByteBuffer data)
	{
		GL13.glCompressedTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, data);
		
	}
	
	public static void glCompressedTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int data_imageSize, long data_buffer_offset)
	{
		GL13.glCompressedTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, data_imageSize, data_buffer_offset);
		
	}
	
	public static void glCopyBufferSubData(int readtarget, int writetarget, long readoffset, long writeoffset, long size)
	{
		GL31.glCopyBufferSubData(readtarget, writetarget, readoffset, writeoffset, size);
		
	}
	
	public static void glCopyImageSubData(int srcName, int srcTarget, int srcLevel, int srcX, int srcY, int srcZ, int dstName, int dstTarget, int dstLevel, int dstX, int dstY, int dstZ, int srcWidth, int srcHeight, int srcDepth)
	{
		GL43.glCopyImageSubData(srcName, srcTarget, srcLevel, srcX, srcY, srcZ, dstName, dstTarget, dstLevel, dstX, dstY, dstZ, srcWidth, srcHeight, srcDepth);
		
	}
	
	public static void glCopyTexImage1D(int target, int level, int internalFormat, int x, int y, int width, int border)
	{
		GL11.glCopyTexImage1D(target, level, internalFormat, x, y, width, border);
		
	}
	
	public static void glCopyTexImage2D(int target, int level, int internalFormat, int x, int y, int width, int height, int border)
	{
		GL11.glCopyTexImage2D(target, level, internalFormat, x, y, width, height, border);
		
	}
	
	public static void glCopyTexSubImage1D(int target, int level, int xoffset, int x, int y, int width)
	{
		GL11.glCopyTexSubImage1D(target, level, xoffset, x, y, width);
		
	}
	
	public static void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height)
	{
		GL11.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
		
	}
	
	public static void glCopyTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int x, int y, int width, int height)
	{
		GL12.glCopyTexSubImage3D(target, level, xoffset, yoffset, zoffset, x, y, width, height);
		
	}
	
	public static int glCreateProgram()
	{
		return GL20.glCreateProgram();
	}
	
	public static int glCreateShader(int type)
	{
		return GL20.glCreateShader(type);
	}
	
	public static int glCreateShaderProgram(int type, ByteBuffer string)
	{
		return GL41.glCreateShaderProgram(type, string);
	}
	
	public static int glCreateShaderProgram(int type, ByteBuffer[] strings)
	{
		return GL41.glCreateShaderProgram(type, strings);
	}
	
	public static int glCreateShaderProgram(int type, CharSequence string)
	{
		return GL41.glCreateShaderProgram(type, string);
	}
	
	public static int glCreateShaderProgram(int type, CharSequence[] strings)
	{
		return GL41.glCreateShaderProgram(type, strings);
	}
	
	public static int glCreateShaderProgram(int type, int count, ByteBuffer strings)
	{
		return GL41.glCreateShaderProgram(type, count, strings);
	}
	
	public static void glCullFace(int mode)
	{
		GL11.glCullFace(mode);
		
	}
	
	//GL D
	
	public static void glDebugMessageCallback(KHRDebugCallback callback)
	{
		GL43.glDebugMessageCallback(callback);
		
	}
	
	public static void glDebugMessageControl(int source, int type, int severity, IntBuffer ids, boolean enabled)
	{
		GL43.glDebugMessageControl(source, type, severity, ids, enabled);
		
	}
	
	public static void glDebugMessageInsert(int source, int type, int id, int severity, ByteBuffer buf)
	{
		GL43.glDebugMessageInsert(source, type, id, severity, buf);
		
	}
	
	public static void glDebugMessageInsert(int source, int type, int id, int severity, CharSequence buf)
	{
		GL43.glDebugMessageInsert(source, type, id, severity, buf);
		
	}
	
	public static void glDeleteBuffers(int buffer)
	{
		GL15.glDeleteBuffers(buffer);
		
	}
	
	public static void glDeleteBuffers(IntBuffer buffers)
	{
		GL15.glDeleteBuffers(buffers);
		
	}
	
	public static void glDeleteBuffers(VertexBufferObject buffer)
	{
		glDeleteBuffers(buffer.id);
		
	}
	
	public static void glDeleteFramebuffers(int framebuffer)
	{
		GL30.glDeleteFramebuffers(framebuffer);
		
	}
	
	public static void glDeleteFramebuffers(IntBuffer framebuffers)
	{
		GL30.glDeleteFramebuffers(framebuffers);
		
	}
	
	public static void glDeleteProgram(int program)
	{
		GL20.glDeleteProgram(program);
		
	}
	
	public static void glDeleteProgram(GLProgram program)
	{
		glDeleteProgram(program.getId());
		
	}
	
	public static void glDeleteProgramPipelines(int pipeline)
	{
		GL41.glDeleteProgramPipelines(pipeline);
		
	}
	
	public static void glDeleteProgramPipelines(IntBuffer pipelines)
	{
		GL41.glDeleteProgramPipelines(pipelines);
		
	}
	
	public static void glDeleteQueries(int id)
	{
		GL15.glDeleteQueries(id);
		
	}
	
	public static void glDeleteQueries(IntBuffer ids)
	{
		GL15.glDeleteQueries(ids);
		
	}
	
	public static void glDeleteRenderbuffers(int renderbuffer)
	{
		GL30.glDeleteRenderbuffers(renderbuffer);
		
	}
	
	public static void glDeleteRenderbuffers(IntBuffer renderbuffers)
	{
		GL30.glDeleteRenderbuffers(renderbuffers);
		
	}
	
	public static void glDeleteSamplers(int sampler)
	{
		GL33.glDeleteSamplers(sampler);
		
	}
	
	public static void glDeleteSamplers(IntBuffer samplers)
	{
		GL33.glDeleteSamplers(samplers);
		
	}
	
	public static void glDeleteShader(int shader)
	{
		GL20.glDeleteShader(shader);
		
	}
	
	public static void glDeleteSync(GLSync sync)
	{
		GL32.glDeleteSync(sync);
		
	}
	
	public static void glDeleteTextures(int texture)
	{
		GL11.glDeleteTextures(texture);
		
	}
	
	public static void glDeleteTextures(IntBuffer textures)
	{
		GL11.glDeleteTextures(textures);
		
	}
	
	public static void glDeleteTransformFeedbacks(int id)
	{
		GL40.glDeleteTransformFeedbacks(id);
		
	}
	
	public static void glDeleteTransformFeedbacks(IntBuffer ids)
	{
		GL40.glDeleteTransformFeedbacks(ids);
		
	}
	
	public static void glDeleteVertexArrays(int array)
	{
		GL30.glDeleteVertexArrays(array);
		
	}
	
	public static void glDeleteVertexArrays(IntBuffer arrays)
	{
		GL30.glDeleteVertexArrays(arrays);
		
	}
	
	public static void glDepthFunc(int func)
	{
		GL11.glDepthFunc(func);
		
	}
	
	public static void glDepthMask(boolean flag)
	{
		GL11.glDepthMask(flag);
		
	}
	
	public static void glDepthRange(double zNear, double zFar)
	{
		GL11.glDepthRange(zNear, zFar);
		
	}
	
	public static void glDepthRangeArray(int first, DoubleBuffer v)
	{
		GL41.glDepthRangeArray(first, v);
		
	}
	
	public static void glDepthRangef(float n, float f)
	{
		GL41.glDepthRangef(n, f);
		
	}
	
	public static void glDepthRangeIndexed(int index, double n, double f)
	{
		GL41.glDepthRangeIndexed(index, n, f);
		
	}
	
	public static void glDetachShader(int program, int shader)
	{
		GL20.glDetachShader(program, shader);
		
	}
	
	public static void glDisable(int cap)
	{
		GL11.glDisable(cap);
		
	}
	
	public static void glDisablei(int target, int index)
	{
		GL30.glDisablei(target, index);
		
	}
	
	public static void glDisableVertexAttribArray(int index)
	{
		GL20.glDisableVertexAttribArray(index);
		
	}
	
	public static void glDispatchCompute(int num_groups_x, int num_groups_y, int num_groups_z)
	{
		GL43.glDispatchCompute(num_groups_x, num_groups_y, num_groups_z);
		
	}
	
	public static void glDispatchComputeIndirect(long indirect)
	{
		GL43.glDispatchComputeIndirect(indirect);
		
	}
	
	public static void glDrawArrays(int mode, int first, int count)
	{
		GL11.glDrawArrays(mode, first, count);
		
	}
	
	public static void glDrawArraysIndirect(int mode, ByteBuffer indirect)
	{
		GL40.glDrawArraysIndirect(mode, indirect);
		
	}
	
	public static void glDrawArraysIndirect(int mode, IntBuffer indirect)
	{
		GL40.glDrawArraysIndirect(mode, indirect);
		
	}
	
	public static void glDrawArraysIndirect(int mode, long indirect_buffer_offset)
	{
		GL40.glDrawArraysIndirect(mode, indirect_buffer_offset);
		
	}
	
	public static void glDrawArraysInstanced(int mode, int first, int count, int primcount)
	{
		GL31.glDrawArraysInstanced(mode, first, count, primcount);
		
	}
	
	public static void glDrawArraysInstancedBaseInstance(int mode, int first, int count, int primcount, int baseinstance)
	{
		GL42.glDrawArraysInstancedBaseInstance(mode, first, count, primcount, baseinstance);
		
	}
	
	public static void glDrawBuffer(int mode)
	{
		GL11.glDrawBuffer(mode);
		
	}
	
	public static void glDrawBuffers(int buffer)
	{
		GL20.glDrawBuffers(buffer);
		
	}
	
	public static void glDrawBuffers(IntBuffer buffers)
	{
		GL20.glDrawBuffers(buffers);
		
	}
	
	public static void glDrawElements(int mode, ByteBuffer indices)
	{
		GL11.glDrawElements(mode, indices);
		
	}
	
	public static void glDrawElements(int mode, int count, int type, ByteBuffer indices)
	{
		GL11.glDrawElements(mode, count, type, indices);
		
	}
	
	public static void glDrawElements(int mode, int indices_count, int type, long indices_buffer_offset)
	{
		GL11.glDrawElements(mode, indices_count, type, indices_buffer_offset);
		
	}
	
	public static void glDrawElements(int mode, IntBuffer indices)
	{
		GL11.glDrawElements(mode, indices);
		
	}
	
	public static void glDrawElements(int mode, ShortBuffer indices)
	{
		GL11.glDrawElements(mode, indices);
		
	}
	
	public static void glDrawElementsBaseVertex(int mode, ByteBuffer indices, int basevertex)
	{
		GL32.glDrawElementsBaseVertex(mode, indices, basevertex);
		
	}
	
	public static void glDrawElementsBaseVertex(int mode, int indices_count, int type, long indices_buffer_offset, int basevertex)
	{
		GL32.glDrawElementsBaseVertex(mode, indices_count, type, indices_buffer_offset, basevertex);
		
	}
	
	public static void glDrawElementsBaseVertex(int mode, IntBuffer indices, int basevertex)
	{
		GL32.glDrawElementsBaseVertex(mode, indices, basevertex);
		
	}
	
	public static void glDrawElementsBaseVertex(int mode, ShortBuffer indices, int basevertex)
	{
		GL32.glDrawElementsBaseVertex(mode, indices, basevertex);
		
	}
	
	public static void glDrawElementsIndirect(int mode, int type, ByteBuffer indirect)
	{
		GL40.glDrawElementsIndirect(mode, type, indirect);
		
	}
	
	public static void glDrawElementsIndirect(int mode, int type, IntBuffer indirect)
	{
		GL40.glDrawElementsIndirect(mode, type, indirect);
		
	}
	
	public static void glDrawElementsIndirect(int mode, int type, long indirect_buffer_offset)
	{
		GL40.glDrawElementsIndirect(mode, type, indirect_buffer_offset);
		
	}
	
	public static void glDrawElementsInstanced(int mode, ByteBuffer indices, int primcount)
	{
		GL31.glDrawElementsInstanced(mode, indices, primcount);
		
	}
	
	public static void glDrawElementsInstanced(int mode, int indices_count, int type, long indices_buffer_offset, int primcount)
	{
		GL31.glDrawElementsInstanced(mode, indices_count, type, indices_buffer_offset, primcount);
		
	}
	
	public static void glDrawElementsInstanced(int mode, IntBuffer indices, int primcount)
	{
		GL31.glDrawElementsInstanced(mode, indices, primcount);
		
	}
	
	public static void glDrawElementsInstanced(int mode, ShortBuffer indices, int primcount)
	{
		GL31.glDrawElementsInstanced(mode, indices, primcount);
		
	}
	
	public static void glDrawElementsInstancedBaseInstance(int mode, ByteBuffer indices, int primcount, int baseinstance)
	{
		GL42.glDrawElementsInstancedBaseInstance(mode, indices, primcount, baseinstance);
		
	}
	
	public static void glDrawElementsInstancedBaseInstance(int mode, int indices_count, int type, long indices_buffer_offset, int primcount, int baseinstance)
	{
		GL42.glDrawElementsInstancedBaseInstance(mode, indices_count, type, indices_buffer_offset, primcount, baseinstance);
		
	}
	
	public static void glDrawElementsInstancedBaseInstance(int mode, IntBuffer indices, int primcount, int baseinstance)
	{
		GL42.glDrawElementsInstancedBaseInstance(mode, indices, primcount, baseinstance);
		
	}
	
	public static void glDrawElementsInstancedBaseInstance(int mode, ShortBuffer indices, int primcount, int baseinstance)
	{
		GL42.glDrawElementsInstancedBaseInstance(mode, indices, primcount, baseinstance);
		
	}
	
	public static void glDrawElementsInstancedBaseVertex(int mode, ByteBuffer indices, int primcount, int basevertex)
	{
		GL32.glDrawElementsInstancedBaseVertex(mode, indices, primcount, basevertex);
		
	}
	
	public static void glDrawElementsInstancedBaseVertex(int mode, int indices_count, int type, long indices_buffer_offset, int primcount, int basevertex)
	{
		GL32.glDrawElementsInstancedBaseVertex(mode, indices_count, type, indices_buffer_offset, primcount, basevertex);
		
	}
	
	public static void glDrawElementsInstancedBaseVertex(int mode, IntBuffer indices, int primcount, int basevertex)
	{
		GL32.glDrawElementsInstancedBaseVertex(mode, indices, primcount, basevertex);
		
	}
	
	public static void glDrawElementsInstancedBaseVertex(int mode, ShortBuffer indices, int primcount, int basevertex)
	{
		GL32.glDrawElementsInstancedBaseVertex(mode, indices, primcount, basevertex);
		
	}
	
	public static void glDrawElementsInstancedBaseVertexBaseInstance(int mode, ByteBuffer indices, int primcount, int basevertex, int baseinstance)
	{
		GL42.glDrawElementsInstancedBaseVertexBaseInstance(mode, indices, primcount, basevertex, baseinstance);
		
	}
	
	public static void glDrawElementsInstancedBaseVertexBaseInstance(int mode, int indices_count, int type, long indices_buffer_offset, int primcount, int basevertex, int baseinstance)
	{
		GL42.glDrawElementsInstancedBaseVertexBaseInstance(mode, indices_count, type, indices_buffer_offset, primcount, basevertex, baseinstance);
		
	}
	
	public static void glDrawElementsInstancedBaseVertexBaseInstance(int mode, IntBuffer indices, int primcount, int basevertex, int baseinstance)
	{
		GL42.glDrawElementsInstancedBaseVertexBaseInstance(mode, indices, primcount, basevertex, baseinstance);
		
	}
	
	public static void glDrawElementsInstancedBaseVertexBaseInstance(int mode, ShortBuffer indices, int primcount, int basevertex, int baseinstance)
	{
		GL42.glDrawElementsInstancedBaseVertexBaseInstance(mode, indices, primcount, basevertex, baseinstance);
		
	}
	
	public static void glDrawRangeElements(int mode, int start, int end, ByteBuffer indices)
	{
		GL12.glDrawRangeElements(mode, start, end, indices);
		
	}
	
	public static void glDrawRangeElements(int mode, int start, int end, int indices_count, int type, long indices_buffer_offset)
	{
		GL12.glDrawRangeElements(mode, start, end, indices_count, type, indices_buffer_offset);
		
	}
	
	public static void glDrawRangeElements(int mode, int start, int end, IntBuffer indices)
	{
		GL12.glDrawRangeElements(mode, start, end, indices);
		
	}
	
	public static void glDrawRangeElements(int mode, int start, int end, ShortBuffer indices)
	{
		GL12.glDrawRangeElements(mode, start, end, indices);
		
	}
	
	public static void glDrawRangeElementsBaseVertex(int mode, int start, int end, ByteBuffer indices, int basevertex)
	{
		GL32.glDrawRangeElementsBaseVertex(mode, start, end, indices, basevertex);
		
	}
	
	public static void glDrawRangeElementsBaseVertex(int mode, int start, int end, int indices_count, int type, long indices_buffer_offset, int basevertex)
	{
		GL32.glDrawRangeElementsBaseVertex(mode, start, end, indices_count, type, indices_buffer_offset, basevertex);
		
	}
	
	public static void glDrawRangeElementsBaseVertex(int mode, int start, int end, IntBuffer indices, int basevertex)
	{
		GL32.glDrawRangeElementsBaseVertex(mode, start, end, indices, basevertex);
		
	}
	
	public static void glDrawRangeElementsBaseVertex(int mode, int start, int end, ShortBuffer indices, int basevertex)
	{
		GL32.glDrawRangeElementsBaseVertex(mode, start, end, indices, basevertex);
		
	}
	
	public static void glDrawTransformFeedback(int mode, int id)
	{
		GL40.glDrawTransformFeedback(mode, id);
		
	}
	
	public static void glDrawTransformFeedbackInstanced(int mode, int id, int primcount)
	{
		GL42.glDrawTransformFeedbackInstanced(mode, id, primcount);
		
	}
	
	public static void glDrawTransformFeedbackStream(int mode, int id, int stream)
	{
		GL40.glDrawTransformFeedbackStream(mode, id, stream);
		
	}
	
	public static void glDrawTransformFeedbackStreamInstanced(int mode, int id, int stream, int primcount)
	{
		GL42.glDrawTransformFeedbackStreamInstanced(mode, id, stream, primcount);
		
	}
	
	//GL E
	
	public static void glEnable(int cap)
	{
		GL11.glEnable(cap);
		
	}
	
	public static void glEnablei(int target, int index)
	{
		GL30.glEnablei(target, index);
		
	}
	
	public static void glEnableVertexAttribArray(int index)
	{
		GL20.glEnableVertexAttribArray(index);
		
	}
	
	public static void glEndConditionalRender()
	{
		GL30.glEndConditionalRender();
		
	}
	
	public static void glEndQuery(int target)
	{
		GL15.glEndQuery(target);
		
	}
	
	public static void glEndQueryIndexed(int target, int index)
	{
		GL40.glEndQueryIndexed(target, index);
		
	}
	
	public static void glEndTransformFeedback()
	{
		GL30.glEndTransformFeedback();
		
	}
	
	//GL F
	
	public static GLSync glFenceSync(int condition, int flags)
	{
		return GL32.glFenceSync(condition, flags);
	}
	
	public static void glFinish()
	{
		GL11.glFinish();
		
	}
	
	public static void glFlush()
	{
		GL11.glFlush();
		
	}
	
	public static void glFlushMappedBufferRange(int target, long offset, long length)
	{
		GL30.glFlushMappedBufferRange(target, offset, length);
		
	}
	
	public static void glFramebufferParameteri(int target, int pname, int param)
	{
		GL43.glFramebufferParameteri(target, pname, param);
		
	}
	
	public static void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer)
	{
		GL30.glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer);
		
	}
	
	public static void glFramebufferTexture(int target, int attachment, int texture, int level)
	{
		GL32.glFramebufferTexture(target, attachment, texture, level);
		
	}
	
	public static void glFramebufferTexture1D(int target, int attachment, int textarget, int texture, int level)
	{
		GL30.glFramebufferTexture1D(target, attachment, textarget, texture, level);
		
	}
	
	public static void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level)
	{
		GL30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
		
	}
	
	public static void glFramebufferTexture3D(int target, int attachment, int textarget, int texture, int level, int zoffset)
	{
		GL30.glFramebufferTexture3D(target, attachment, textarget, texture, level, zoffset);
		
	}
	
	public static void glFramebufferTextureLayer(int target, int attachment, int texture, int level, int layer)
	{
		GL30.glFramebufferTextureLayer(target, attachment, texture, level, layer);
		
	}
	
	public static void glFrontFace(int mode)
	{
		GL11.glFrontFace(mode);
		
	}
	
	//GL G
	
	public static int glGenBuffers()
	{
		return GL15.glGenBuffers();
	}
	
	public static void glGenBuffers(IntBuffer buffers)
	{
		GL15.glGenBuffers(buffers);
		
	}
	
	public static void glGenerateMipmap(int target)
	{
		GL30.glGenerateMipmap(target);
		
	}
	
	public static int glGenFramebuffers()
	{
		return GL30.glGenFramebuffers();
	}
	
	public static void glGenFramebuffers(IntBuffer framebuffers)
	{
		GL30.glGenFramebuffers(framebuffers);
		
	}
	
	public static int glGenProgramPipelines()
	{
		return GL41.glGenProgramPipelines();
	}
	
	public static void glGenProgramPipelines(IntBuffer pipelines)
	{
		GL41.glGenProgramPipelines(pipelines);
		
	}
	
	public static int glGenQueries()
	{
		return GL15.glGenQueries();
	}
	
	public static void glGenQueries(IntBuffer ids)
	{
		GL15.glGenQueries(ids);
		
	}
	
	public static int glGenRenderbuffers()
	{
		return GL30.glGenRenderbuffers();
	}
	
	public static void glGenRenderbuffers(IntBuffer renderbuffers)
	{
		GL30.glGenRenderbuffers(renderbuffers);
		
	}
	
	public static int glGenSamplers()
	{
		return GL33.glGenSamplers();
	}
	
	public static void glGenSamplers(IntBuffer samplers)
	{
		GL33.glGenSamplers(samplers);
		
	}
	
	public static int glGenTextures()
	{
		return GL11.glGenTextures();
	}
	
	public static void glGenTextures(IntBuffer textures)
	{
		GL11.glGenTextures(textures);
		
	}
	
	public static int glGenTransformFeedbacks()
	{
		return GL40.glGenTransformFeedbacks();
	}
	
	public static void glGenTransformFeedbacks(IntBuffer ids)
	{
		GL40.glGenTransformFeedbacks(ids);
		
	}
	
	public static int glGenVertexArrays()
	{
		return GL30.glGenVertexArrays();
	}
	
	public static void glGenVertexArrays(IntBuffer arrays)
	{
		GL30.glGenVertexArrays(arrays);
		
	}
	
	public static int glGetActiveAtomicCounterBuffer(int program, int bufferIndex, int pname)
	{
		return GL42.glGetActiveAtomicCounterBuffer(program, bufferIndex, pname);
	}
	
	public static void glGetActiveAtomicCounterBuffer(int program, int bufferIndex, int pname, IntBuffer params)
	{
		GL42.glGetActiveAtomicCounterBuffer(program, bufferIndex, pname, params);
		
	}
	
	public static String glGetActiveAttrib(int program, int index, int maxLength)
	{
		return GL20.glGetActiveAttrib(program, index, maxLength);
	}
	
	public static String glGetActiveAttrib(int program, int index, int maxLength, IntBuffer sizeType)
	{
		return GL20.glGetActiveAttrib(program, index, maxLength, sizeType);
	}
	
	public static void glGetActiveAttrib(int program, int index, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name)
	{
		GL20.glGetActiveAttrib(program, index, length, size, type, name);
		
	}
	
	public static int glGetActiveAttribSize(int program, int index)
	{
		return GL20.glGetActiveAttribSize(program, index);
	}
	
	public static int glGetActiveAttribType(int program, int index)
	{
		return GL20.glGetActiveAttribType(program, index);
	}
	
	public static String glGetActiveSubroutineName(int program, int shadertype, int index, int bufsize)
	{
		return GL40.glGetActiveSubroutineName(program, shadertype, index, bufsize);
	}
	
	public static void glGetActiveSubroutineName(int program, int shadertype, int index, IntBuffer length, ByteBuffer name)
	{
		GL40.glGetActiveSubroutineName(program, shadertype, index, length, name);
		
	}
	
	public static void glGetActiveSubroutineUniform(int program, int shadertype, int index, int pname, IntBuffer values)
	{
		GL40.glGetActiveSubroutineUniform(program, shadertype, index, pname, values);
		
	}
	
	public static int glGetActiveSubroutineUniformi(int program, int shadertype, int index, int pname)
	{
		return GL40.glGetActiveSubroutineUniformi(program, shadertype, index, pname);
	}
	
	public static String glGetActiveSubroutineUniformName(int program, int shadertype, int index, int bufsize)
	{
		return GL40.glGetActiveSubroutineUniformName(program, shadertype, index, bufsize);
	}
	
	public static void glGetActiveSubroutineUniformName(int program, int shadertype, int index, IntBuffer length, ByteBuffer name)
	{
		GL40.glGetActiveSubroutineUniformName(program, shadertype, index, length, name);
		
	}
	
	public static String glGetActiveUniform(int program, int index, int maxLength)
	{
		return GL20.glGetActiveUniform(program, index, maxLength);
	}
	
	public static String glGetActiveUniform(int program, int index, int maxLength, IntBuffer sizeType)
	{
		return GL20.glGetActiveUniform(program, index, maxLength, sizeType);
	}
	
	public static void glGetActiveUniform(int program, int index, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name)
	{
		GL20.glGetActiveUniform(program, index, length, size, type, name);
		
	}
	
	public static void glGetActiveUniformBlock(int program, int uniformBlockIndex, int pname, IntBuffer params)
	{
		GL31.glGetActiveUniformBlock(program, uniformBlockIndex, pname, params);
		
	}
	
	public static int glGetActiveUniformBlocki(int program, int uniformBlockIndex, int pname)
	{
		return GL31.glGetActiveUniformBlocki(program, uniformBlockIndex, pname);
	}
	
	public static String glGetActiveUniformBlockName(int program, int uniformBlockIndex, int bufSize)
	{
		return GL31.glGetActiveUniformBlockName(program, uniformBlockIndex, bufSize);
	}
	
	public static void glGetActiveUniformBlockName(int program, int uniformBlockIndex, IntBuffer length, ByteBuffer uniformBlockName)
	{
		GL31.glGetActiveUniformBlockName(program, uniformBlockIndex, length, uniformBlockName);
		
	}
	
	public static String glGetActiveUniformName(int program, int uniformIndex, int bufSize)
	{
		return GL31.glGetActiveUniformName(program, uniformIndex, bufSize);
	}
	
	public static void glGetActiveUniformName(int program, int uniformIndex, IntBuffer length, ByteBuffer uniformName)
	{
		GL31.glGetActiveUniformName(program, uniformIndex, length, uniformName);
		
	}
	
	public static void glGetActiveUniforms(int program, IntBuffer uniformIndices, int pname, IntBuffer params)
	{
		GL31.glGetActiveUniforms(program, uniformIndices, pname, params);
		
	}
	
	public static int glGetActiveUniformsi(int program, int uniformIndex, int pname)
	{
		return GL31.glGetActiveUniformsi(program, uniformIndex, pname);
	}
	
	public static int glGetActiveUniformType(int program, int index)
	{
		return GL20.glGetActiveUniformType(program, index);
	}
	
	public static void glGetAttachedShaders(int program, IntBuffer count, IntBuffer shaders)
	{
		GL20.glGetAttachedShaders(program, count, shaders);
		
	}
	
	public static int glGetAttribLocation(int program, ByteBuffer name)
	{
		return GL20.glGetAttribLocation(program, name);
	}
	
	public static int glGetAttribLocation(int program, CharSequence name)
	{
		return GL20.glGetAttribLocation(program, name);
	}
	
	public static boolean glGetBoolean(int pname)
	{
		return GL11.glGetBoolean(pname);
	}
	
	public static void glGetBoolean(int pname, ByteBuffer params)
	{
		GL11.glGetBoolean(pname, params);
		
	}
	
	public static boolean glGetBoolean(int value, int index)
	{
		return GL30.glGetBoolean(value, index);
	}
	
	public static void glGetBoolean(int value, int index, ByteBuffer data)
	{
		GL30.glGetBoolean(value, index, data);
		
	}
	
	public static void glGetBufferParameter(int target, int pname, IntBuffer params)
	{
		GL15.glGetBufferParameter(target, pname, params);
		
	}
	
	public static void glGetBufferParameter(int target, int pname, LongBuffer params)
	{
		GL32.glGetBufferParameter(target, pname, params);
		
	}
	
	public static int glGetBufferParameteri(int target, int pname)
	{
		return GL15.glGetBufferParameteri(target, pname);
	}
	
	public static long glGetBufferParameteri64(int target, int pname)
	{
		return GL32.glGetBufferParameteri64(target, pname);
	}
	
	public static ByteBuffer glGetBufferPointer(int target, int pname)
	{
		return GL15.glGetBufferPointer(target, pname);
	}
	
	public static void glGetBufferSubData(int target, long offset, ByteBuffer data)
	{
		GL15.glGetBufferSubData(target, offset, data);
		
	}
	
	public static void glGetBufferSubData(int target, long offset, DoubleBuffer data)
	{
		GL15.glGetBufferSubData(target, offset, data);
		
	}
	
	public static void glGetBufferSubData(int target, long offset, FloatBuffer data)
	{
		GL15.glGetBufferSubData(target, offset, data);
		
	}
	
	public static void glGetBufferSubData(int target, long offset, IntBuffer data)
	{
		GL15.glGetBufferSubData(target, offset, data);
		
	}
	
	public static void glGetBufferSubData(int target, long offset, ShortBuffer data)
	{
		GL15.glGetBufferSubData(target, offset, data);
		
	}
	
	public static void glGetClipPlane(int plane, DoubleBuffer equation)
	{
		GL11.glGetClipPlane(plane, equation);
		
	}
	
	public static void glGetCompressedTexImage(int target, int lod, ByteBuffer img)
	{
		GL13.glGetCompressedTexImage(target, lod, img);
		
	}
	
	public static void glGetCompressedTexImage(int target, int lod, IntBuffer img)
	{
		GL13.glGetCompressedTexImage(target, lod, img);
		
	}
	
	public static void glGetCompressedTexImage(int target, int lod, long img_buffer_offset)
	{
		GL13.glGetCompressedTexImage(target, lod, img_buffer_offset);
		
	}
	
	public static void glGetCompressedTexImage(int target, int lod, ShortBuffer img)
	{
		GL13.glGetCompressedTexImage(target, lod, img);
		
	}
	
	public static int glGetDebugMessageLog(int count, IntBuffer sources, IntBuffer types, IntBuffer ids, IntBuffer severities, IntBuffer lengths, ByteBuffer messageLog)
	{
		return GL43.glGetDebugMessageLog(count, sources, types, ids, severities, lengths, messageLog);
	}
	
	public static double glGetDouble(int pname)
	{
		return GL11.glGetDouble(pname);
	}
	
	public static void glGetDouble(int pname, DoubleBuffer params)
	{
		GL11.glGetDouble(pname, params);
		
	}
	
	public static double glGetDouble(int target, int index)
	{
		return GL41.glGetDouble(target, index);
	}
	
	public static void glGetDouble(int target, int index, DoubleBuffer data)
	{
		GL41.glGetDouble(target, index, data);
		
	}
	
	public static int glGetError()
	{
		return GL11.glGetError();
	}
	
	public static float glGetFloat(int pname)
	{
		return GL11.glGetFloat(pname);
	}
	
	public static void glGetFloat(int pname, FloatBuffer params)
	{
		GL11.glGetFloat(pname, params);
		
	}
	
	public static float glGetFloat(int target, int index)
	{
		return GL41.glGetFloat(target, index);
	}
	
	public static void glGetFloat(int target, int index, FloatBuffer data)
	{
		GL41.glGetFloat(target, index, data);
		
	}
	
	public static int glGetFragDataIndex(int program, ByteBuffer name)
	{
		return GL33.glGetFragDataIndex(program, name);
	}
	
	public static int glGetFragDataIndex(int program, CharSequence name)
	{
		return GL33.glGetFragDataIndex(program, name);
	}
	
	public static int glGetFragDataLocation(int program, ByteBuffer name)
	{
		return GL30.glGetFragDataLocation(program, name);
	}
	
	public static int glGetFragDataLocation(int program, CharSequence name)
	{
		return GL30.glGetFragDataLocation(program, name);
	}
	
	public static void glGetFramebufferAttachmentParameter(int target, int attachment, int pname, IntBuffer params)
	{
		GL30.glGetFramebufferAttachmentParameter(target, attachment, pname, params);
		
	}
	
	public static int glGetFramebufferAttachmentParameteri(int target, int attachment, int pname)
	{
		return GL30.glGetFramebufferAttachmentParameteri(target, attachment, pname);
	}
	
	public static void glGetFramebufferParameter(int target, int pname, IntBuffer params)
	{
		GL43.glGetFramebufferParameter(target, pname, params);
		
	}
	
	public static int glGetFramebufferParameteri(int target, int pname)
	{
		return GL43.glGetFramebufferParameteri(target, pname);
	}
	
	public static int glGetInteger(int pname)
	{
		return GL11.glGetInteger(pname);
	}
	
	public static int glGetInteger(int value, int index)
	{
		return GL30.glGetInteger(value, index);
	}
	
	public static void glGetInteger(int value, int index, IntBuffer data)
	{
		GL30.glGetInteger(value, index, data);
		
	}
	
	public static void glGetInteger(int pname, IntBuffer params)
	{
		GL11.glGetInteger(pname, params);
		
	}
	
	public static long glGetInteger64(int pname)
	{
		return GL32.glGetInteger64(pname);
	}
	
	public static long glGetInteger64(int value, int index)
	{
		return GL32.glGetInteger64(value, index);
	}
	
	public static void glGetInteger64(int value, int index, LongBuffer data)
	{
		GL32.glGetInteger64(value, index, data);
		
	}
	
	public static void glGetInteger64(int pname, LongBuffer data)
	{
		GL32.glGetInteger64(pname, data);
		
	}
	
	public static int glGetInternalformat(int target, int internalformat, int pname)
	{
		return GL42.glGetInternalformat(target, internalformat, pname);
	}
	
	public static void glGetInternalformat(int target, int internalformat, int pname, IntBuffer params)
	{
		GL42.glGetInternalformat(target, internalformat, pname, params);
		
	}
	
	public static void glGetInternalformat(int target, int internalformat, int pname, LongBuffer params)
	{
		GL43.glGetInternalformat(target, internalformat, pname, params);
		
	}
	
	public static long glGetInternalformati64(int target, int internalformat, int pname)
	{
		return GL43.glGetInternalformati64(target, internalformat, pname);
	}
	
	public static void glGetMultisample(int pname, int index, FloatBuffer val)
	{
		GL32.glGetMultisample(pname, index, val);
		
	}
	
	public static String glGetObjectLabel(int identifier, int name, int bufSize)
	{
		return GL43.glGetObjectLabel(identifier, name, bufSize);
	}
	
	public static void glGetObjectLabel(int identifier, int name, IntBuffer length, ByteBuffer label)
	{
		GL43.glGetObjectLabel(identifier, name, length, label);
		
	}
	
	public static String glGetObjectPtrLabel(PointerWrapper ptr, int bufSize)
	{
		return GL43.glGetObjectPtrLabel(ptr, bufSize);
	}
	
	public static void glGetObjectPtrLabel(PointerWrapper ptr, IntBuffer length, ByteBuffer label)
	{
		GL43.glGetObjectPtrLabel(ptr, length, label);
		
	}
	
	public static void glGetProgram(int program, int pname, IntBuffer params)
	{
		GL20.glGetProgram(program, pname, params);
		
	}
	
	public static void glGetProgramBinary(int program, IntBuffer length, IntBuffer binaryFormat, ByteBuffer binary)
	{
		GL41.glGetProgramBinary(program, length, binaryFormat, binary);
		
	}
	
	public static int glGetProgrami(int program, int pname)
	{
		return GL20.glGetProgrami(program, pname);
	}
	
	public static String glGetProgramInfoLog(int program, int maxLength)
	{
		return GL20.glGetProgramInfoLog(program, maxLength);
	}
	
	public static void glGetProgramInfoLog(int program, IntBuffer length, ByteBuffer infoLog)
	{
		GL20.glGetProgramInfoLog(program, length, infoLog);
		
	}
	
	public static void glGetProgramInterface(int program, int programInterface, int pname, IntBuffer params)
	{
		GL43.glGetProgramInterface(program, programInterface, pname, params);
		
	}
	
	public static int glGetProgramInterfacei(int program, int programInterface, int pname)
	{
		return GL43.glGetProgramInterfacei(program, programInterface, pname);
	}
	
	public static void glGetProgramPipeline(int pipeline, int pname, IntBuffer params)
	{
		GL41.glGetProgramPipeline(pipeline, pname, params);
		
	}
	
	public static int glGetProgramPipelinei(int pipeline, int pname)
	{
		return GL41.glGetProgramPipelinei(pipeline, pname);
	}
	
	public static String glGetProgramPipelineInfoLog(int pipeline, int bufSize)
	{
		return GL41.glGetProgramPipelineInfoLog(pipeline, bufSize);
	}
	
	public static void glGetProgramPipelineInfoLog(int pipeline, IntBuffer length, ByteBuffer infoLog)
	{
		GL41.glGetProgramPipelineInfoLog(pipeline, length, infoLog);
		
	}
	
	public static void glGetProgramResource(int program, int programInterface, int index, IntBuffer props, IntBuffer length, IntBuffer params)
	{
		GL43.glGetProgramResource(program, programInterface, index, props, length, params);
		
	}
	
	public static int glGetProgramResourceIndex(int program, int programInterface, ByteBuffer name)
	{
		return GL43.glGetProgramResourceIndex(program, programInterface, name);
	}
	
	public static int glGetProgramResourceIndex(int program, int programInterface, CharSequence name)
	{
		return GL43.glGetProgramResourceIndex(program, programInterface, name);
	}
	
	public static int glGetProgramResourceLocation(int program, int programInterface, ByteBuffer name)
	{
		return GL43.glGetProgramResourceLocation(program, programInterface, name);
	}
	
	public static int glGetProgramResourceLocation(int program, int programInterface, CharSequence name)
	{
		return GL43.glGetProgramResourceLocation(program, programInterface, name);
	}
	
	public static int glGetProgramResourceLocationIndex(int program, int programInterface, ByteBuffer name)
	{
		return GL43.glGetProgramResourceLocationIndex(program, programInterface, name);
	}
	
	public static int glGetProgramResourceLocationIndex(int program, int programInterface, CharSequence name)
	{
		return GL43.glGetProgramResourceLocationIndex(program, programInterface, name);
	}
	
	public static String glGetProgramResourceName(int program, int programInterface, int index, int bufSize)
	{
		return GL43.glGetProgramResourceName(program, programInterface, index, bufSize);
	}
	
	public static void glGetProgramResourceName(int program, int programInterface, int index, IntBuffer length, ByteBuffer name)
	{
		GL43.glGetProgramResourceName(program, programInterface, index, length, name);
		
	}
	
	public static void glGetProgramStage(int program, int shadertype, int pname, IntBuffer values)
	{
		GL40.glGetProgramStage(program, shadertype, pname, values);
		
	}
	
	public static int glGetProgramStagei(int program, int shadertype, int pname)
	{
		return GL40.glGetProgramStagei(program, shadertype, pname);
	}
	
	public static void glGetQuery(int target, int pname, IntBuffer params)
	{
		GL15.glGetQuery(target, pname, params);
		
	}
	
	public static int glGetQueryi(int target, int pname)
	{
		return GL15.glGetQueryi(target, pname);
	}
	
	public static void glGetQueryIndexed(int target, int index, int pname, IntBuffer params)
	{
		GL40.glGetQueryIndexed(target, index, pname, params);
		
	}
	
	public static int glGetQueryIndexedi(int target, int index, int pname)
	{
		return GL40.glGetQueryIndexedi(target, index, pname);
	}
	
	public static void glGetQueryObject(int id, int pname, IntBuffer params)
	{
		GL15.glGetQueryObject(id, pname, params);
		
	}
	
	public static void glGetQueryObject(int id, int pname, LongBuffer params)
	{
		GL33.glGetQueryObject(id, pname, params);
		
	}
	
	public static int glGetQueryObjecti(int id, int pname)
	{
		return GL15.glGetQueryObjecti(id, pname);
	}
	
	public static long glGetQueryObjecti64(int id, int pname)
	{
		return GL33.glGetQueryObjecti64(id, pname);
	}
	
	public static void glGetQueryObjectu(int id, int pname, IntBuffer params)
	{
		GL15.glGetQueryObjectu(id, pname, params);
		
	}
	
	public static void glGetQueryObjectu(int id, int pname, LongBuffer params)
	{
		GL33.glGetQueryObjectu(id, pname, params);
		
	}
	
	public static int glGetQueryObjectui(int id, int pname)
	{
		return GL15.glGetQueryObjectui(id, pname);
	}
	
	public static long glGetQueryObjectui64(int id, int pname)
	{
		return GL33.glGetQueryObjectui64(id, pname);
	}
	
	public static void glGetRenderbufferParameter(int target, int pname, IntBuffer params)
	{
		GL30.glGetRenderbufferParameter(target, pname, params);
		
	}
	
	public static int glGetRenderbufferParameteri(int target, int pname)
	{
		return GL30.glGetRenderbufferParameteri(target, pname);
	}
	
	public static void glGetSamplerParameter(int sampler, int pname, FloatBuffer params)
	{
		GL33.glGetSamplerParameter(sampler, pname, params);
		
	}
	
	public static void glGetSamplerParameter(int sampler, int pname, IntBuffer params)
	{
		GL33.glGetSamplerParameter(sampler, pname, params);
		
	}
	
	public static float glGetSamplerParameterf(int sampler, int pname)
	{
		return GL33.glGetSamplerParameterf(sampler, pname);
	}
	
	public static int glGetSamplerParameteri(int sampler, int pname)
	{
		return GL33.glGetSamplerParameteri(sampler, pname);
	}
	
	public static void glGetSamplerParameterI(int sampler, int pname, IntBuffer params)
	{
		GL33.glGetSamplerParameterI(sampler, pname, params);
		
	}
	
	public static int glGetSamplerParameterIi(int sampler, int pname)
	{
		return GL33.glGetSamplerParameterIi(sampler, pname);
	}
	
	public static void glGetSamplerParameterIu(int sampler, int pname, IntBuffer params)
	{
		GL33.glGetSamplerParameterIu(sampler, pname, params);
		
	}
	
	public static int glGetSamplerParameterIui(int sampler, int pname)
	{
		return GL33.glGetSamplerParameterIui(sampler, pname);
	}
	
	public static void glGetShader(int shader, int pname, IntBuffer params)
	{
		GL20.glGetShader(shader, pname, params);
		
	}
	
	public static int glGetShaderi(int shader, int pname)
	{
		return GL20.glGetShaderi(shader, pname);
	}
	
	public static String glGetShaderInfoLog(int shader, int maxLength)
	{
		return GL20.glGetShaderInfoLog(shader, maxLength);
	}
	
	public static void glGetShaderInfoLog(int shader, IntBuffer length, ByteBuffer infoLog)
	{
		GL20.glGetShaderInfoLog(shader, length, infoLog);
		
	}
	
	public static void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision)
	{
		GL41.glGetShaderPrecisionFormat(shadertype, precisiontype, range, precision);
		
	}
	
	public static String glGetShaderSource(int shader, int maxLength)
	{
		return GL20.glGetShaderSource(shader, maxLength);
	}
	
	public static void glGetShaderSource(int shader, IntBuffer length, ByteBuffer source)
	{
		GL20.glGetShaderSource(shader, length, source);
		
	}
	
	public static String glGetString(int name)
	{
		return GL11.glGetString(name);
	}
	
	public static String glGetStringi(int name, int index)
	{
		return GL30.glGetStringi(name, index);
	}
	
	public static int glGetSubroutineIndex(int program, int shadertype, ByteBuffer name)
	{
		return GL40.glGetSubroutineIndex(program, shadertype, name);
	}
	
	public static int glGetSubroutineIndex(int program, int shadertype, CharSequence name)
	{
		return GL40.glGetSubroutineIndex(program, shadertype, name);
	}
	
	public static int glGetSubroutineUniformLocation(int program, int shadertype, ByteBuffer name)
	{
		return GL40.glGetSubroutineUniformLocation(program, shadertype, name);
	}
	
	public static int glGetSubroutineUniformLocation(int program, int shadertype, CharSequence name)
	{
		return GL40.glGetSubroutineUniformLocation(program, shadertype, name);
	}
	
	public static void glGetSync(GLSync sync, int pname, IntBuffer length, IntBuffer values)
	{
		GL32.glGetSync(sync, pname, length, values);
		
	}
	
	public static int glGetSynci(GLSync sync, int pname)
	{
		return GL32.glGetSynci(sync, pname);
	}
	
	public static void glGetTexImage(int target, int level, int format, int type, ByteBuffer pixels)
	{
		GL11.glGetTexImage(target, level, format, type, pixels);
		
	}
	
	public static void glGetTexImage(int target, int level, int format, int type, DoubleBuffer pixels)
	{
		GL11.glGetTexImage(target, level, format, type, pixels);
		
	}
	
	public static void glGetTexImage(int target, int level, int format, int type, FloatBuffer pixels)
	{
		GL11.glGetTexImage(target, level, format, type, pixels);
		
	}
	
	public static void glGetTexImage(int target, int level, int format, int type, IntBuffer pixels)
	{
		GL11.glGetTexImage(target, level, format, type, pixels);
		
	}
	
	public static void glGetTexImage(int target, int level, int format, int type, long pixels_buffer_offset)
	{
		GL11.glGetTexImage(target, level, format, type, pixels_buffer_offset);
		
	}
	
	public static void glGetTexImage(int target, int level, int format, int type, ShortBuffer pixels)
	{
		GL11.glGetTexImage(target, level, format, type, pixels);
		
	}
	
	public static void glGetTexLevelParameter(int target, int level, int pname, FloatBuffer params)
	{
		GL11.glGetTexLevelParameter(target, level, pname, params);
		
	}
	
	public static void glGetTexLevelParameter(int target, int level, int pname, IntBuffer params)
	{
		GL11.glGetTexLevelParameter(target, level, pname, params);
		
	}
	
	public static float glGetTexLevelParameterf(int target, int level, int pname)
	{
		return GL11.glGetTexLevelParameterf(target, level, pname);
	}
	
	public static int glGetTexLevelParameteri(int target, int level, int pname)
	{
		return GL11.glGetTexLevelParameteri(target, level, pname);
	}
	
	public static void glGetTexParameter(int target, int pname, FloatBuffer params)
	{
		GL11.glGetTexParameter(target, pname, params);
		
	}
	
	public static void glGetTexParameter(int target, int pname, IntBuffer params)
	{
		GL11.glGetTexParameter(target, pname, params);
		
	}
	
	public static float glGetTexParameterf(int target, int pname)
	{
		return GL11.glGetTexParameterf(target, pname);
	}
	
	public static int glGetTexParameteri(int target, int pname)
	{
		return GL11.glGetTexParameteri(target, pname);
	}
	
	public static void glGetTexParameterI(int target, int pname, IntBuffer params)
	{
		GL30.glGetTexParameterI(target, pname, params);
		
	}
	
	public static int glGetTexParameterIi(int target, int pname)
	{
		return GL30.glGetTexParameterIi(target, pname);
	}
	
	public static void glGetTexParameterIu(int target, int pname, IntBuffer params)
	{
		GL30.glGetTexParameterIu(target, pname, params);
		
	}
	
	public static int glGetTexParameterIui(int target, int pname)
	{
		return GL30.glGetTexParameterIui(target, pname);
	}
	
	public static String glGetTransformFeedbackVarying(int program, int index, int bufSize, IntBuffer size, IntBuffer type)
	{
		return GL30.glGetTransformFeedbackVarying(program, index, bufSize, size, type);
	}
	
	public static void glGetTransformFeedbackVarying(int program, int index, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name)
	{
		GL30.glGetTransformFeedbackVarying(program, index, length, size, type, name);
		
	}
	
	public static void glGetUniform(int program, int location, DoubleBuffer params)
	{
		GL40.glGetUniform(program, location, params);
		
	}
	
	public static void glGetUniform(int program, int location, FloatBuffer params)
	{
		GL20.glGetUniform(program, location, params);
		
	}
	
	public static void glGetUniform(int program, int location, IntBuffer params)
	{
		GL20.glGetUniform(program, location, params);
		
	}
	
	public static int glGetUniformBlockIndex(int program, ByteBuffer uniformBlockName)
	{
		return GL31.glGetUniformBlockIndex(program, uniformBlockName);
	}
	
	public static int glGetUniformBlockIndex(int program, CharSequence uniformBlockName)
	{
		return GL31.glGetUniformBlockIndex(program, uniformBlockName);
	}
	
	public static void glGetUniformIndices(int program, ByteBuffer uniformNames, IntBuffer uniformIndices)
	{
		GL31.glGetUniformIndices(program, uniformNames, uniformIndices);
		
	}
	
	public static void glGetUniformIndices(int program, CharSequence[] uniformNames, IntBuffer uniformIndices)
	{
		GL31.glGetUniformIndices(program, uniformNames, uniformIndices);
		
	}
	
	public static int glGetUniformLocation(int program, ByteBuffer name)
	{
		return GL20.glGetUniformLocation(program, name);
	}
	
	public static int glGetUniformLocation(int program, CharSequence name)
	{
		return GL20.glGetUniformLocation(program, name);
	}
	
	public static void glGetUniformSubroutineu(int shadertype, int location, IntBuffer params)
	{
		GL40.glGetUniformSubroutineu(shadertype, location, params);
		
	}
	
	public static int glGetUniformSubroutineui(int shadertype, int location)
	{
		return GL40.glGetUniformSubroutineui(shadertype, location);
	}
	
	public static void glGetUniformu(int program, int location, IntBuffer params)
	{
		GL30.glGetUniformu(program, location, params);
		
	}
	
	public static void glGetVertexAttrib(int index, int pname, DoubleBuffer params)
	{
		GL20.glGetVertexAttrib(index, pname, params);
		
	}
	
	public static void glGetVertexAttrib(int index, int pname, FloatBuffer params)
	{
		GL20.glGetVertexAttrib(index, pname, params);
		
	}
	
	public static void glGetVertexAttrib(int index, int pname, IntBuffer params)
	{
		GL20.glGetVertexAttrib(index, pname, params);
		
	}
	
	public static void glGetVertexAttribI(int index, int pname, IntBuffer params)
	{
		GL30.glGetVertexAttribI(index, pname, params);
		
	}
	
	public static void glGetVertexAttribIu(int index, int pname, IntBuffer params)
	{
		GL30.glGetVertexAttribIu(index, pname, params);
		
	}
	
	public static void glGetVertexAttribL(int index, int pname, DoubleBuffer params)
	{
		GL41.glGetVertexAttribL(index, pname, params);
		
	}
	
	public static void glGetVertexAttribPointer(int index, int pname, ByteBuffer pointer)
	{
		GL20.glGetVertexAttribPointer(index, pname, pointer);
		
	}
	
	public static ByteBuffer glGetVertexAttribPointer(int index, int pname, long result_size)
	{
		return GL20.glGetVertexAttribPointer(index, pname, result_size);
	}
	
	//GL H
	
	public static void glHint(int target, int mode)
	{
		GL11.glHint(target, mode);
		
	}
	
	//GL I
	
	public static void glInvalidateBufferData(int buffer)
	{
		GL43.glInvalidateBufferData(buffer);
		
	}
	
	public static void glInvalidateBufferSubData(int buffer, long offset, long length)
	{
		GL43.glInvalidateBufferSubData(buffer, offset, length);
		
	}
	
	public static void glInvalidateFramebuffer(int target, IntBuffer attachments)
	{
		GL43.glInvalidateFramebuffer(target, attachments);
		
	}
	
	public static void glInvalidateSubFramebuffer(int target, IntBuffer attachments, int x, int y, int width, int height)
	{
		GL43.glInvalidateSubFramebuffer(target, attachments, x, y, width, height);
		
	}
	
	public static void glInvalidateTexImage(int texture, int level)
	{
		GL43.glInvalidateTexImage(texture, level);
		
	}
	
	public static void glInvalidateTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth)
	{
		GL43.glInvalidateTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth);
		
	}
	
	public static boolean glIsBuffer(int buffer)
	{
		return GL15.glIsBuffer(buffer);
	}
	
	public static boolean glIsEnabled(int cap)
	{
		return GL11.glIsEnabled(cap);
	}
	
	public static boolean glIsEnabledi(int target, int index)
	{
		return GL30.glIsEnabledi(target, index);
	}
	
	public static boolean glIsFramebuffer(int framebuffer)
	{
		return GL30.glIsFramebuffer(framebuffer);
	}
	
	public static boolean glIsProgram(int program)
	{
		return GL20.glIsProgram(program);
	}
	
	public static boolean glIsProgramPipeline(int pipeline)
	{
		return GL41.glIsProgramPipeline(pipeline);
	}
	
	public static boolean glIsQuery(int id)
	{
		return GL15.glIsQuery(id);
	}
	
	public static boolean glIsRenderbuffer(int renderbuffer)
	{
		return GL30.glIsRenderbuffer(renderbuffer);
	}
	
	public static boolean glIsSampler(int sampler)
	{
		return GL33.glIsSampler(sampler);
	}
	
	public static boolean glIsShader(int shader)
	{
		return GL20.glIsShader(shader);
	}
	
	public static boolean glIsSync(GLSync sync)
	{
		return GL32.glIsSync(sync);
	}
	
	public static boolean glIsTexture(int texture)
	{
		return GL11.glIsTexture(texture);
	}
	
	public static boolean glIsTransformFeedback(int id)
	{
		return GL40.glIsTransformFeedback(id);
	}
	
	public static boolean glIsVertexArray(int array)
	{
		return GL30.glIsVertexArray(array);
	}
	
	//GL L
	
	public static void glLineWidth(float width)
	{
		GL11.glLineWidth(width);
		
	}
	
	public static void glLinkProgram(int program)
	{
		GL20.glLinkProgram(program);
		
	}
	
	public static void glLinkProgram(GLProgram program)
	{
		glLinkProgram(program.getId());
		
	}
	
	public static void glLogicOp(int opcode)
	{
		GL11.glLogicOp(opcode);
		
	}
	
	//GL M
	
	public static ByteBuffer glMapBuffer(int target, int access, ByteBuffer old_buffer)
	{
		return GL15.glMapBuffer(target, access, old_buffer);
	}
	
	public static ByteBuffer glMapBuffer(int target, int access, long length, ByteBuffer old_buffer)
	{
		return GL15.glMapBuffer(target, access, length, old_buffer);
	}
	
	public static ByteBuffer glMapBufferRange(int target, long offset, long length, int access, ByteBuffer old_buffer)
	{
		return GL30.glMapBufferRange(target, offset, length, access, old_buffer);
	}
	
	public static void glMemoryBarrier(int barriers)
	{
		GL42.glMemoryBarrier(barriers);
		
	}
	
	public static void glMinSampleShading(float value)
	{
		GL40.glMinSampleShading(value);
		
	}
	
	public static void glMultiDrawArrays(int mode, IntBuffer piFirst, IntBuffer piCount)
	{
		GL14.glMultiDrawArrays(mode, piFirst, piCount);
		
	}
	
	public static void glMultiDrawArraysIndirect(int mode, ByteBuffer indirect, int primcount, int stride)
	{
		GL43.glMultiDrawArraysIndirect(mode, indirect, primcount, stride);
		
	}
	
	public static void glMultiDrawArraysIndirect(int mode, IntBuffer indirect, int primcount, int stride)
	{
		GL43.glMultiDrawArraysIndirect(mode, indirect, primcount, stride);
		
	}
	
	public static void glMultiDrawArraysIndirect(int mode, long indirect_buffer_offset, int primcount, int stride)
	{
		GL43.glMultiDrawArraysIndirect(mode, indirect_buffer_offset, primcount, stride);
		
	}
	
	public static void glMultiDrawElementsIndirect(int mode, int type, ByteBuffer indirect, int primcount, int stride)
	{
		GL43.glMultiDrawElementsIndirect(mode, type, indirect, primcount, stride);
		
	}
	
	public static void glMultiDrawElementsIndirect(int mode, int type, IntBuffer indirect, int primcount, int stride)
	{
		GL43.glMultiDrawElementsIndirect(mode, type, indirect, primcount, stride);
		
	}
	
	public static void glMultiDrawElementsIndirect(int mode, int type, long indirect_buffer_offset, int primcount, int stride)
	{
		GL43.glMultiDrawElementsIndirect(mode, type, indirect_buffer_offset, primcount, stride);
		
	}
	
	//GL N
	
	//GL O
	
	public static void glObjectLabel(int identifier, int name, ByteBuffer label)
	{
		GL43.glObjectLabel(identifier, name, label);
		
	}
	
	public static void glObjectLabel(int identifier, int name, CharSequence label)
	{
		GL43.glObjectLabel(identifier, name, label);
		
	}
	
	public static void glObjectPtrLabel(PointerWrapper ptr, ByteBuffer label)
	{
		GL43.glObjectPtrLabel(ptr, label);
		
	}
	
	public static void glObjectPtrLabel(PointerWrapper ptr, CharSequence label)
	{
		GL43.glObjectPtrLabel(ptr, label);
		
	}
	
	//GL P
	
	public static void glPatchParameter(int pname, FloatBuffer values)
	{
		GL40.glPatchParameter(pname, values);
		
	}
	
	public static void glPatchParameteri(int pname, int value)
	{
		GL40.glPatchParameteri(pname, value);
		
	}
	
	public static void glPauseTransformFeedback()
	{
		GL40.glPauseTransformFeedback();
		
	}
	
	public static void glPixelStoref(int pname, float param)
	{
		GL11.glPixelStoref(pname, param);
		
	}
	
	public static void glPixelStorei(int pname, int param)
	{
		GL11.glPixelStorei(pname, param);
		
	}
	
	public static void glPointParameter(int pname, FloatBuffer params)
	{
		GL14.glPointParameter(pname, params);
		
	}
	
	public static void glPointParameter(int pname, IntBuffer params)
	{
		GL14.glPointParameter(pname, params);
		
	}
	
	public static void glPointParameterf(int pname, float param)
	{
		GL14.glPointParameterf(pname, param);
		
	}
	
	public static void glPointParameteri(int pname, int param)
	{
		GL14.glPointParameteri(pname, param);
		
	}
	
	public static void glPointSize(float size)
	{
		GL11.glPointSize(size);
		
	}
	
	public static void glPolygonMode(int face, int mode)
	{
		GL11.glPolygonMode(face, mode);
		
	}
	
	public static void glPolygonOffset(float factor, float units)
	{
		GL11.glPolygonOffset(factor, units);
		
	}
	
	public static void glPopDebugGroup()
	{
		GL43.glPopDebugGroup();
		
	}
	
	public static void glPrimitiveRestartIndex(int index)
	{
		GL31.glPrimitiveRestartIndex(index);
		
	}
	
	public static void glProgramBinary(int program, int binaryFormat, ByteBuffer binary)
	{
		GL41.glProgramBinary(program, binaryFormat, binary);
		
	}
	
	public static void glProgramParameteri(int program, int pname, int value)
	{
		GL41.glProgramParameteri(program, pname, value);
		
	}
	
	public static void glProgramUniform1(int program, int location, DoubleBuffer value)
	{
		GL41.glProgramUniform1(program, location, value);
		
	}
	
	public static void glProgramUniform1(int program, int location, FloatBuffer value)
	{
		GL41.glProgramUniform1(program, location, value);
		
	}
	
	public static void glProgramUniform1(int program, int location, IntBuffer value)
	{
		GL41.glProgramUniform1(program, location, value);
		
	}
	
	public static void glProgramUniform1d(int program, int location, double v0)
	{
		GL41.glProgramUniform1d(program, location, v0);
		
	}
	
	public static void glProgramUniform1f(int program, int location, float v0)
	{
		GL41.glProgramUniform1f(program, location, v0);
		
	}
	
	public static void glProgramUniform1i(int program, int location, int v0)
	{
		GL41.glProgramUniform1i(program, location, v0);
		
	}
	
	public static void glProgramUniform1u(int program, int location, IntBuffer value)
	{
		GL41.glProgramUniform1u(program, location, value);
		
	}
	
	public static void glProgramUniform1ui(int program, int location, int v0)
	{
		GL41.glProgramUniform1ui(program, location, v0);
		
	}
	
	public static void glProgramUniform2(int program, int location, DoubleBuffer value)
	{
		GL41.glProgramUniform2(program, location, value);
		
	}
	
	public static void glProgramUniform2(int program, int location, FloatBuffer value)
	{
		GL41.glProgramUniform2(program, location, value);
		
	}
	
	public static void glProgramUniform2(int program, int location, IntBuffer value)
	{
		GL41.glProgramUniform2(program, location, value);
		
	}
	
	public static void glProgramUniform2d(int program, int location, double v0, double v1)
	{
		GL41.glProgramUniform2d(program, location, v0, v1);
		
	}
	
	public static void glProgramUniform2f(int program, int location, float v0, float v1)
	{
		GL41.glProgramUniform2f(program, location, v0, v1);
		
	}
	
	public static void glProgramUniform2i(int program, int location, int v0, int v1)
	{
		GL41.glProgramUniform2i(program, location, v0, v1);
		
	}
	
	public static void glProgramUniform2u(int program, int location, IntBuffer value)
	{
		GL41.glProgramUniform2u(program, location, value);
		
	}
	
	public static void glProgramUniform2ui(int program, int location, int v0, int v1)
	{
		GL41.glProgramUniform2ui(program, location, v0, v1);
		
	}
	
	public static void glProgramUniform3(int program, int location, DoubleBuffer value)
	{
		GL41.glProgramUniform3(program, location, value);
		
	}
	
	public static void glProgramUniform3(int program, int location, FloatBuffer value)
	{
		GL41.glProgramUniform3(program, location, value);
		
	}
	
	public static void glProgramUniform3(int program, int location, IntBuffer value)
	{
		GL41.glProgramUniform3(program, location, value);
		
	}
	
	public static void glProgramUniform3d(int program, int location, double v0, double v1, double v2)
	{
		GL41.glProgramUniform3d(program, location, v0, v1, v2);
		
	}
	
	public static void glProgramUniform3f(int program, int location, float v0, float v1, float v2)
	{
		GL41.glProgramUniform3f(program, location, v0, v1, v2);
		
	}
	
	public static void glProgramUniform3i(int program, int location, int v0, int v1, int v2)
	{
		GL41.glProgramUniform3i(program, location, v0, v1, v2);
		
	}
	
	public static void glProgramUniform3u(int program, int location, IntBuffer value)
	{
		GL41.glProgramUniform3u(program, location, value);
		
	}
	
	public static void glProgramUniform3ui(int program, int location, int v0, int v1, int v2)
	{
		GL41.glProgramUniform3ui(program, location, v0, v1, v2);
		
	}
	
	public static void glProgramUniform4(int program, int location, DoubleBuffer value)
	{
		GL41.glProgramUniform4(program, location, value);
		
	}
	
	public static void glProgramUniform4(int program, int location, FloatBuffer value)
	{
		GL41.glProgramUniform4(program, location, value);
		
	}
	
	public static void glProgramUniform4(int program, int location, IntBuffer value)
	{
		GL41.glProgramUniform4(program, location, value);
		
	}
	
	public static void glProgramUniform4d(int program, int location, double v0, double v1, double v2, double v3)
	{
		GL41.glProgramUniform4d(program, location, v0, v1, v2, v3);
		
	}
	
	public static void glProgramUniform4f(int program, int location, float v0, float v1, float v2, float v3)
	{
		GL41.glProgramUniform4f(program, location, v0, v1, v2, v3);
		
	}
	
	public static void glProgramUniform4i(int program, int location, int v0, int v1, int v2, int v3)
	{
		GL41.glProgramUniform4i(program, location, v0, v1, v2, v3);
		
	}
	
	public static void glProgramUniform4u(int program, int location, IntBuffer value)
	{
		GL41.glProgramUniform4u(program, location, value);
		
	}
	
	public static void glProgramUniform4ui(int program, int location, int v0, int v1, int v2, int v3)
	{
		GL41.glProgramUniform4ui(program, location, v0, v1, v2, v3);
		
	}
	
	public static void glProgramUniformMatrix2(int program, int location, boolean transpose, DoubleBuffer value)
	{
		GL41.glProgramUniformMatrix2(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix2(int program, int location, boolean transpose, FloatBuffer value)
	{
		GL41.glProgramUniformMatrix2(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix2x3(int program, int location, boolean transpose, DoubleBuffer value)
	{
		GL41.glProgramUniformMatrix2x3(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix2x3(int program, int location, boolean transpose, FloatBuffer value)
	{
		GL41.glProgramUniformMatrix2x3(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix2x4(int program, int location, boolean transpose, DoubleBuffer value)
	{
		GL41.glProgramUniformMatrix2x4(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix2x4(int program, int location, boolean transpose, FloatBuffer value)
	{
		GL41.glProgramUniformMatrix2x4(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix3(int program, int location, boolean transpose, DoubleBuffer value)
	{
		GL41.glProgramUniformMatrix3(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix3(int program, int location, boolean transpose, FloatBuffer value)
	{
		GL41.glProgramUniformMatrix3(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix3x2(int program, int location, boolean transpose, DoubleBuffer value)
	{
		GL41.glProgramUniformMatrix3x2(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix3x2(int program, int location, boolean transpose, FloatBuffer value)
	{
		GL41.glProgramUniformMatrix3x2(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix3x4(int program, int location, boolean transpose, DoubleBuffer value)
	{
		GL41.glProgramUniformMatrix3x4(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix3x4(int program, int location, boolean transpose, FloatBuffer value)
	{
		GL41.glProgramUniformMatrix3x4(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix4(int program, int location, boolean transpose, DoubleBuffer value)
	{
		GL41.glProgramUniformMatrix4(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix4(int program, int location, boolean transpose, FloatBuffer value)
	{
		GL41.glProgramUniformMatrix4(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix4x2(int program, int location, boolean transpose, DoubleBuffer value)
	{
		GL41.glProgramUniformMatrix4x2(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix4x2(int program, int location, boolean transpose, FloatBuffer value)
	{
		GL41.glProgramUniformMatrix4x2(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix4x3(int program, int location, boolean transpose, DoubleBuffer value)
	{
		GL41.glProgramUniformMatrix4x3(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix4x3(int program, int location, boolean transpose, FloatBuffer value)
	{
		GL41.glProgramUniformMatrix4x3(program, location, transpose, value);
		
	}
	
	public static void glProvokingVertex(int mode)
	{
		GL32.glProvokingVertex(mode);
		
	}
	
	public static void glPushDebugGroup(int source, int id, ByteBuffer message)
	{
		GL43.glPushDebugGroup(source, id, message);
		
	}
	
	public static void glPushDebugGroup(int source, int id, CharSequence message)
	{
		GL43.glPushDebugGroup(source, id, message);
		
	}
	
	//GL Q
	
	public static void glQueryCounter(int id, int target)
	{
		GL33.glQueryCounter(id, target);
		
	}
	
	//GL R
	
	public static void glReadBuffer(int mode)
	{
		GL11.glReadBuffer(mode);
		
	}
	
	public static void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels)
	{
		GL11.glReadPixels(x, y, width, height, format, type, pixels);
		
	}
	
	public static void glReadPixels(int x, int y, int width, int height, int format, int type, DoubleBuffer pixels)
	{
		GL11.glReadPixels(x, y, width, height, format, type, pixels);
		
	}
	
	public static void glReadPixels(int x, int y, int width, int height, int format, int type, FloatBuffer pixels)
	{
		GL11.glReadPixels(x, y, width, height, format, type, pixels);
		
	}
	
	public static void glReadPixels(int x, int y, int width, int height, int format, int type, IntBuffer pixels)
	{
		GL11.glReadPixels(x, y, width, height, format, type, pixels);
		
	}
	
	public static void glReadPixels(int x, int y, int width, int height, int format, int type, long pixels_buffer_offset)
	{
		GL11.glReadPixels(x, y, width, height, format, type, pixels_buffer_offset);
		
	}
	
	public static void glReadPixels(int x, int y, int width, int height, int format, int type, ShortBuffer pixels)
	{
		GL11.glReadPixels(x, y, width, height, format, type, pixels);
		
	}
	
	public static void glReleaseShaderCompiler()
	{
		GL41.glReleaseShaderCompiler();
		
	}
	
	public static void glRenderbufferStorage(int target, int internalformat, int width, int height)
	{
		GL30.glRenderbufferStorage(target, internalformat, width, height);
		
	}
	
	public static void glRenderbufferStorageMultisample(int target, int samples, int internalformat, int width, int height)
	{
		GL30.glRenderbufferStorageMultisample(target, samples, internalformat, width, height);
		
	}
	
	public static void glResumeTransformFeedback()
	{
		GL40.glResumeTransformFeedback();
		
	}
	
	//GL S
	
	public static void glSampleCoverage(float value, boolean invert)
	{
		GL13.glSampleCoverage(value, invert);
		
	}
	
	public static void glSampleMaski(int index, int mask)
	{
		GL32.glSampleMaski(index, mask);
		
	}
	
	public static void glSamplerParameter(int sampler, int pname, FloatBuffer params)
	{
		GL33.glSamplerParameter(sampler, pname, params);
		
	}
	
	public static void glSamplerParameter(int sampler, int pname, IntBuffer params)
	{
		GL33.glSamplerParameter(sampler, pname, params);
		
	}
	
	public static void glSamplerParameterf(int sampler, int pname, float param)
	{
		GL33.glSamplerParameterf(sampler, pname, param);
		
	}
	
	public static void glSamplerParameteri(int sampler, int pname, int param)
	{
		GL33.glSamplerParameteri(sampler, pname, param);
		
	}
	
	public static void glSamplerParameterI(int sampler, int pname, IntBuffer params)
	{
		GL33.glSamplerParameterI(sampler, pname, params);
		
	}
	
	public static void glSamplerParameterIu(int sampler, int pname, IntBuffer params)
	{
		GL33.glSamplerParameterIu(sampler, pname, params);
		
	}
	
	public static void glScissor(int x, int y, int width, int height)
	{
		GL11.glScissor(x, y, width, height);
		
	}
	
	public static void glScissorArray(int first, IntBuffer v)
	{
		GL41.glScissorArray(first, v);
		
	}
	
	public static void glScissorIndexed(int index, int left, int bottom, int width, int height)
	{
		GL41.glScissorIndexed(index, left, bottom, width, height);
		
	}
	
	public static void glScissorIndexed(int index, IntBuffer v)
	{
		GL41.glScissorIndexed(index, v);
		
	}
	
	public static void glShaderBinary(IntBuffer shaders, int binaryformat, ByteBuffer binary)
	{
		GL41.glShaderBinary(shaders, binaryformat, binary);
		
	}
	
	public static void glShaderSource(int shader, ByteBuffer string)
	{
		GL20.glShaderSource(shader, string);
		
	}
	
	public static void glShaderSource(int shader, CharSequence string)
	{
		GL20.glShaderSource(shader, string);
		
	}
	
	public static void glShaderSource(int shader, CharSequence[] strings)
	{
		GL20.glShaderSource(shader, strings);
		
	}
	
	public static void glShaderStorageBlockBinding(int program, int storageBlockIndex, int storageBlockBinding)
	{
		GL43.glShaderStorageBlockBinding(program, storageBlockIndex, storageBlockBinding);
		
	}
	
	public static void glStencilFunc(int func, int ref, int mask)
	{
		GL11.glStencilFunc(func, ref, mask);
		
	}
	
	public static void glStencilFuncSeparate(int face, int func, int ref, int mask)
	{
		GL20.glStencilFuncSeparate(face, func, ref, mask);
		
	}
	
	public static void glStencilMask(int mask)
	{
		GL11.glStencilMask(mask);
		
	}
	
	public static void glStencilMaskSeparate(int face, int mask)
	{
		GL20.glStencilMaskSeparate(face, mask);
		
	}
	
	public static void glStencilOp(int fail, int zfail, int zpass)
	{
		GL11.glStencilOp(fail, zfail, zpass);
		
	}
	
	public static void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass)
	{
		GL20.glStencilOpSeparate(face, sfail, dpfail, dppass);
		
	}
	
	//GL T
	
	public static void glTexBuffer(int target, int internalformat, int buffer)
	{
		GL31.glTexBuffer(target, internalformat, buffer);
		
	}
	
	public static void glTexBufferRange(int target, int internalformat, int buffer, long offset, long size)
	{
		GL43.glTexBufferRange(target, internalformat, buffer, offset, size);
		
	}
	
	public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, ByteBuffer pixels)
	{
		GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
		
	}
	
	public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, DoubleBuffer pixels)
	{
		GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
		
	}
	
	public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, FloatBuffer pixels)
	{
		GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
		
	}
	
	public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, IntBuffer pixels)
	{
		GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
		
	}
	
	public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, long pixels_buffer_offset)
	{
		GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels_buffer_offset);
		
	}
	
	public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, ShortBuffer pixels)
	{
		GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
		
	}
	
	public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ByteBuffer pixels)
	{
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
		
	}
	
	public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, DoubleBuffer pixels)
	{
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
		
	}
	
	public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, FloatBuffer pixels)
	{
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
		
	}
	
	public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, IntBuffer pixels)
	{
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
		
	}
	
	public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, long pixels_buffer_offset)
	{
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels_buffer_offset);
		
	}
	
	public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ShortBuffer pixels)
	{
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
		
	}
	
	public static void glTexImage2DMultisample(int target, int samples, int internalformat, int width, int height, boolean fixedsamplelocations)
	{
		GL32.glTexImage2DMultisample(target, samples, internalformat, width, height, fixedsamplelocations);
		
	}
	
	public static void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, ByteBuffer pixels)
	{
		GL12.glTexImage3D(target, level, internalFormat, width, height, depth, border, format, type, pixels);
		
	}
	
	public static void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, DoubleBuffer pixels)
	{
		GL12.glTexImage3D(target, level, internalFormat, width, height, depth, border, format, type, pixels);
		
	}
	
	public static void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, FloatBuffer pixels)
	{
		GL12.glTexImage3D(target, level, internalFormat, width, height, depth, border, format, type, pixels);
		
	}
	
	public static void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, IntBuffer pixels)
	{
		GL12.glTexImage3D(target, level, internalFormat, width, height, depth, border, format, type, pixels);
		
	}
	
	public static void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, long pixels_buffer_offset)
	{
		GL12.glTexImage3D(target, level, internalFormat, width, height, depth, border, format, type, pixels_buffer_offset);
		
	}
	
	public static void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, ShortBuffer pixels)
	{
		GL12.glTexImage3D(target, level, internalFormat, width, height, depth, border, format, type, pixels);
		
	}
	
	public static void glTexImage3DMultisample(int target, int samples, int internalformat, int width, int height, int depth, boolean fixedsamplelocations)
	{
		GL32.glTexImage3DMultisample(target, samples, internalformat, width, height, depth, fixedsamplelocations);
		
	}
	
	public static void glTexParameter(int target, int pname, FloatBuffer param)
	{
		GL11.glTexParameter(target, pname, param);
		
	}
	
	public static void glTexParameter(int target, int pname, IntBuffer param)
	{
		GL11.glTexParameter(target, pname, param);
		
	}
	
	public static void glTexParameterf(int target, int pname, float param)
	{
		GL11.glTexParameterf(target, pname, param);
		
	}
	
	public static void glTexParameteri(int target, int pname, int param)
	{
		GL11.glTexParameteri(target, pname, param);
		
	}
	
	public static void glTexParameterI(int target, int pname, IntBuffer params)
	{
		GL30.glTexParameterI(target, pname, params);
		
	}
	
	public static void glTexParameterIi(int target, int pname, int param)
	{
		GL30.glTexParameterIi(target, pname, param);
		
	}
	
	public static void glTexParameterIu(int target, int pname, IntBuffer params)
	{
		GL30.glTexParameterIu(target, pname, params);
		
	}
	
	public static void glTexParameterIui(int target, int pname, int param)
	{
		GL30.glTexParameterIui(target, pname, param);
		
	}
	
	public static void glTexStorage1D(int target, int levels, int internalformat, int width)
	{
		GL42.glTexStorage1D(target, levels, internalformat, width);
		
	}
	
	public static void glTexStorage2D(int target, int levels, int internalformat, int width, int height)
	{
		GL42.glTexStorage2D(target, levels, internalformat, width, height);
		
	}
	
	public static void glTexStorage2DMultisample(int target, int samples, int internalformat, int width, int height, boolean fixedsamplelocations)
	{
		GL43.glTexStorage2DMultisample(target, samples, internalformat, width, height, fixedsamplelocations);
		
	}
	
	public static void glTexStorage3D(int target, int levels, int internalformat, int width, int height, int depth)
	{
		GL42.glTexStorage3D(target, levels, internalformat, width, height, depth);
		
	}
	
	public static void glTexStorage3DMultisample(int target, int samples, int internalformat, int width, int height, int depth, boolean fixedsamplelocations)
	{
		GL43.glTexStorage3DMultisample(target, samples, internalformat, width, height, depth, fixedsamplelocations);
		
	}
	
	public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, ByteBuffer pixels)
	{
		GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
		
	}
	
	public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, DoubleBuffer pixels)
	{
		GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
		
	}
	
	public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, FloatBuffer pixels)
	{
		GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
		
	}
	
	public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, IntBuffer pixels)
	{
		GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
		
	}
	
	public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, long pixels_buffer_offset)
	{
		GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels_buffer_offset);
		
	}
	
	public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, ShortBuffer pixels)
	{
		GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
		
	}
	
	public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ByteBuffer pixels)
	{
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
		
	}
	
	public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, DoubleBuffer pixels)
	{
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
		
	}
	
	public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, FloatBuffer pixels)
	{
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
		
	}
	
	public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, IntBuffer pixels)
	{
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
		
	}
	
	public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, long pixels_buffer_offset)
	{
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels_buffer_offset);
		
	}
	
	public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ShortBuffer pixels)
	{
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
		
	}
	
	public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ByteBuffer pixels)
	{
		GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
		
	}
	
	public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, DoubleBuffer pixels)
	{
		GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
		
	}
	
	public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, FloatBuffer pixels)
	{
		GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
		
	}
	
	public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, IntBuffer pixels)
	{
		GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
		
	}
	
	public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, long pixels_buffer_offset)
	{
		GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels_buffer_offset);
		
	}
	
	public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ShortBuffer pixels)
	{
		GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
		
	}
	
	public static void glTextureView(int texture, int target, int origtexture, int internalformat, int minlevel, int numlevels, int minlayer, int numlayers)
	{
		GL43.glTextureView(texture, target, origtexture, internalformat, minlevel, numlevels, minlayer, numlayers);
		
	}
	
	public static void glTransformFeedbackVaryings(int program, CharSequence[] varyings, int bufferMode)
	{
		GL30.glTransformFeedbackVaryings(program, varyings, bufferMode);
		
	}
	
	public static void glTransformFeedbackVaryings(int program, int count, ByteBuffer varyings, int bufferMode)
	{
		GL30.glTransformFeedbackVaryings(program, count, varyings, bufferMode);
		
	}
	
	//GL U
	
	public static void glUniform1(int location, DoubleBuffer value)
	{
		GL40.glUniform1(location, value);
		
	}
	
	public static void glUniform1(int location, FloatBuffer values)
	{
		GL20.glUniform1(location, values);
		
	}
	
	public static void glUniform1(int location, IntBuffer values)
	{
		GL20.glUniform1(location, values);
		
	}
	
	public static void glUniform1d(int location, double x)
	{
		GL40.glUniform1d(location, x);
		
	}
	
	public static void glUniform1f(int location, float v0)
	{
		GL20.glUniform1f(location, v0);
		
	}
	
	public static void glUniform1i(int location, int v0)
	{
		GL20.glUniform1i(location, v0);
		
	}
	
	public static void glUniform1u(int location, IntBuffer value)
	{
		GL30.glUniform1u(location, value);
		
	}
	
	public static void glUniform1ui(int location, int v0)
	{
		GL30.glUniform1ui(location, v0);
		
	}
	
	public static void glUniform2(int location, DoubleBuffer value)
	{
		GL40.glUniform2(location, value);
		
	}
	
	public static void glUniform2(int location, FloatBuffer values)
	{
		GL20.glUniform2(location, values);
		
	}
	
	public static void glUniform2(int location, IntBuffer values)
	{
		GL20.glUniform2(location, values);
		
	}
	
	public static void glUniform2d(int location, double x, double y)
	{
		GL40.glUniform2d(location, x, y);
		
	}
	
	public static void glUniform2f(int location, float v0, float v1)
	{
		GL20.glUniform2f(location, v0, v1);
		
	}
	
	public static void glUniform2i(int location, int v0, int v1)
	{
		GL20.glUniform2i(location, v0, v1);
		
	}
	
	public static void glUniform2u(int location, IntBuffer value)
	{
		GL30.glUniform2u(location, value);
		
	}
	
	public static void glUniform2ui(int location, int v0, int v1)
	{
		GL30.glUniform2ui(location, v0, v1);
		
	}
	
	public static void glUniform3(int location, DoubleBuffer value)
	{
		GL40.glUniform3(location, value);
		
	}
	
	public static void glUniform3(int location, FloatBuffer values)
	{
		GL20.glUniform3(location, values);
		
	}
	
	public static void glUniform3(int location, IntBuffer values)
	{
		GL20.glUniform3(location, values);
		
	}
	
	public static void glUniform3d(int location, double x, double y, double z)
	{
		GL40.glUniform3d(location, x, y, z);
		
	}
	
	public static void glUniform3f(int location, float v0, float v1, float v2)
	{
		GL20.glUniform3f(location, v0, v1, v2);
		
	}
	
	public static void glUniform3i(int location, int v0, int v1, int v2)
	{
		GL20.glUniform3i(location, v0, v1, v2);
		
	}
	
	public static void glUniform3u(int location, IntBuffer value)
	{
		GL30.glUniform3u(location, value);
		
	}
	
	public static void glUniform3ui(int location, int v0, int v1, int v2)
	{
		GL30.glUniform3ui(location, v0, v1, v2);
		
	}
	
	public static void glUniform4(int location, DoubleBuffer value)
	{
		GL40.glUniform4(location, value);
		
	}
	
	public static void glUniform4(int location, FloatBuffer values)
	{
		GL20.glUniform4(location, values);
		
	}
	
	public static void glUniform4(int location, IntBuffer values)
	{
		GL20.glUniform4(location, values);
		
	}
	
	public static void glUniform4d(int location, double x, double y, double z, double w)
	{
		GL40.glUniform4d(location, x, y, z, w);
		
	}
	
	public static void glUniform4f(int location, float v0, float v1, float v2, float v3)
	{
		GL20.glUniform4f(location, v0, v1, v2, v3);
		
	}
	
	public static void glUniform4i(int location, int v0, int v1, int v2, int v3)
	{
		GL20.glUniform4i(location, v0, v1, v2, v3);
		
	}
	
	public static void glUniform4u(int location, IntBuffer value)
	{
		GL30.glUniform4u(location, value);
		
	}
	
	public static void glUniform4ui(int location, int v0, int v1, int v2, int v3)
	{
		GL30.glUniform4ui(location, v0, v1, v2, v3);
		
	}
	
	public static void glUniformBlockBinding(int program, int uniformBlockIndex, int uniformBlockBinding)
	{
		GL31.glUniformBlockBinding(program, uniformBlockIndex, uniformBlockBinding);
		
	}
	
	public static void glUniformMatrix2(int location, boolean transpose, DoubleBuffer value)
	{
		GL40.glUniformMatrix2(location, transpose, value);
		
	}
	
	public static void glUniformMatrix2(int location, boolean transpose, FloatBuffer matrices)
	{
		GL20.glUniformMatrix2(location, transpose, matrices);
		
	}
	
	public static void glUniformMatrix2x3(int location, boolean transpose, DoubleBuffer value)
	{
		GL40.glUniformMatrix2x3(location, transpose, value);
		
	}
	
	public static void glUniformMatrix2x3(int location, boolean transpose, FloatBuffer matrices)
	{
		GL21.glUniformMatrix2x3(location, transpose, matrices);
		
	}
	
	public static void glUniformMatrix2x4(int location, boolean transpose, DoubleBuffer value)
	{
		GL40.glUniformMatrix2x4(location, transpose, value);
		
	}
	
	public static void glUniformMatrix2x4(int location, boolean transpose, FloatBuffer matrices)
	{
		GL21.glUniformMatrix2x4(location, transpose, matrices);
		
	}
	
	public static void glUniformMatrix3(int location, boolean transpose, DoubleBuffer value)
	{
		GL40.glUniformMatrix3(location, transpose, value);
		
	}
	
	public static void glUniformMatrix3(int location, boolean transpose, FloatBuffer matrices)
	{
		GL20.glUniformMatrix3(location, transpose, matrices);
		
	}
	
	public static void glUniformMatrix3x2(int location, boolean transpose, DoubleBuffer value)
	{
		GL40.glUniformMatrix3x2(location, transpose, value);
		
	}
	
	public static void glUniformMatrix3x2(int location, boolean transpose, FloatBuffer matrices)
	{
		GL21.glUniformMatrix3x2(location, transpose, matrices);
		
	}
	
	public static void glUniformMatrix3x4(int location, boolean transpose, DoubleBuffer value)
	{
		GL40.glUniformMatrix3x4(location, transpose, value);
		
	}
	
	public static void glUniformMatrix3x4(int location, boolean transpose, FloatBuffer matrices)
	{
		GL21.glUniformMatrix3x4(location, transpose, matrices);
		
	}
	
	public static void glUniformMatrix4(int location, boolean transpose, DoubleBuffer value)
	{
		GL40.glUniformMatrix4(location, transpose, value);
		
	}
	
	public static void glUniformMatrix4(int location, boolean transpose, FloatBuffer matrices)
	{
		GL20.glUniformMatrix4(location, transpose, matrices);
		
	}
	
	public static void glUniformMatrix4x2(int location, boolean transpose, DoubleBuffer value)
	{
		GL40.glUniformMatrix4x2(location, transpose, value);
		
	}
	
	public static void glUniformMatrix4x2(int location, boolean transpose, FloatBuffer matrices)
	{
		GL21.glUniformMatrix4x2(location, transpose, matrices);
		
	}
	
	public static void glUniformMatrix4x3(int location, boolean transpose, DoubleBuffer value)
	{
		GL40.glUniformMatrix4x3(location, transpose, value);
		
	}
	
	public static void glUniformMatrix4x3(int location, boolean transpose, FloatBuffer matrices)
	{
		GL21.glUniformMatrix4x3(location, transpose, matrices);
		
	}
	
	public static void glUniformSubroutinesu(int shadertype, IntBuffer indices)
	{
		GL40.glUniformSubroutinesu(shadertype, indices);
		
	}
	
	public static boolean glUnmapBuffer(int target)
	{
		return GL15.glUnmapBuffer(target);
	}
	
	public static void glUseProgram(int program)
	{
		GL20.glUseProgram(program);
		
	}
	
	public static void glUseProgram(GLProgram program)
	{
		glUseProgram(program.getId());
		
	}
	
	public static void glUseProgramStages(int pipeline, int stages, int program)
	{
		GL41.glUseProgramStages(pipeline, stages, program);
		
	}
	
	//GL V
	
	public static void glValidateProgram(int program)
	{
		GL20.glValidateProgram(program);
		
	}
	
	public static void glValidateProgram(GLProgram program)
	{
		glValidateProgram(program.getId());
		
	}
	
	public static void glValidateProgramPipeline(int pipeline)
	{
		GL41.glValidateProgramPipeline(pipeline);
		
	}
	
	public static void glVertexAttrib1d(int index, double x)
	{
		GL20.glVertexAttrib1d(index, x);
		
	}
	
	public static void glVertexAttrib1f(int index, float x)
	{
		GL20.glVertexAttrib1f(index, x);
		
	}
	
	public static void glVertexAttrib1s(int index, short x)
	{
		GL20.glVertexAttrib1s(index, x);
		
	}
	
	public static void glVertexAttrib2d(int index, double x, double y)
	{
		GL20.glVertexAttrib2d(index, x, y);
		
	}
	
	public static void glVertexAttrib2f(int index, float x, float y)
	{
		GL20.glVertexAttrib2f(index, x, y);
		
	}
	
	public static void glVertexAttrib2s(int index, short x, short y)
	{
		GL20.glVertexAttrib2s(index, x, y);
		
	}
	
	public static void glVertexAttrib3d(int index, double x, double y, double z)
	{
		GL20.glVertexAttrib3d(index, x, y, z);
		
	}
	
	public static void glVertexAttrib3f(int index, float x, float y, float z)
	{
		GL20.glVertexAttrib3f(index, x, y, z);
		
	}
	
	public static void glVertexAttrib3s(int index, short x, short y, short z)
	{
		GL20.glVertexAttrib3s(index, x, y, z);
		
	}
	
	public static void glVertexAttrib4d(int index, double x, double y, double z, double w)
	{
		GL20.glVertexAttrib4d(index, x, y, z, w);
		
	}
	
	public static void glVertexAttrib4f(int index, float x, float y, float z, float w)
	{
		GL20.glVertexAttrib4f(index, x, y, z, w);
		
	}
	
	public static void glVertexAttrib4Nub(int index, byte x, byte y, byte z, byte w)
	{
		GL20.glVertexAttrib4Nub(index, x, y, z, w);
		
	}
	
	public static void glVertexAttrib4s(int index, short x, short y, short z, short w)
	{
		GL20.glVertexAttrib4s(index, x, y, z, w);
		
	}
	
	public static void glVertexAttribBinding(int attribindex, int bindingindex)
	{
		GL43.glVertexAttribBinding(attribindex, bindingindex);
		
	}
	
	public static void glVertexAttribDivisor(int index, int divisor)
	{
		GL33.glVertexAttribDivisor(index, divisor);
		
	}
	
	public static void glVertexAttribFormat(int attribindex, int size, int type, boolean normalized, int relativeoffset)
	{
		GL43.glVertexAttribFormat(attribindex, size, type, normalized, relativeoffset);
		
	}
	
	public static void glVertexAttribI1(int index, IntBuffer v)
	{
		GL30.glVertexAttribI1(index, v);
		
	}
	
	public static void glVertexAttribI1i(int index, int x)
	{
		GL30.glVertexAttribI1i(index, x);
		
	}
	
	public static void glVertexAttribI1u(int index, IntBuffer v)
	{
		GL30.glVertexAttribI1u(index, v);
		
	}
	
	public static void glVertexAttribI1ui(int index, int x)
	{
		GL30.glVertexAttribI1ui(index, x);
		
	}
	
	public static void glVertexAttribI2(int index, IntBuffer v)
	{
		GL30.glVertexAttribI2(index, v);
		
	}
	
	public static void glVertexAttribI2i(int index, int x, int y)
	{
		GL30.glVertexAttribI2i(index, x, y);
		
	}
	
	public static void glVertexAttribI2u(int index, IntBuffer v)
	{
		GL30.glVertexAttribI2u(index, v);
		
	}
	
	public static void glVertexAttribI2ui(int index, int x, int y)
	{
		GL30.glVertexAttribI2ui(index, x, y);
		
	}
	
	public static void glVertexAttribI3(int index, IntBuffer v)
	{
		GL30.glVertexAttribI3(index, v);
		
	}
	
	public static void glVertexAttribI3i(int index, int x, int y, int z)
	{
		GL30.glVertexAttribI3i(index, x, y, z);
		
	}
	
	public static void glVertexAttribI3u(int index, IntBuffer v)
	{
		GL30.glVertexAttribI3u(index, v);
		
	}
	
	public static void glVertexAttribI3ui(int index, int x, int y, int z)
	{
		GL30.glVertexAttribI3ui(index, x, y, z);
		
	}
	
	public static void glVertexAttribI4(int index, ByteBuffer v)
	{
		GL30.glVertexAttribI4(index, v);
		
	}
	
	public static void glVertexAttribI4(int index, IntBuffer v)
	{
		GL30.glVertexAttribI4(index, v);
		
	}
	
	public static void glVertexAttribI4(int index, ShortBuffer v)
	{
		GL30.glVertexAttribI4(index, v);
		
	}
	
	public static void glVertexAttribI4i(int index, int x, int y, int z, int w)
	{
		GL30.glVertexAttribI4i(index, x, y, z, w);
		
	}
	
	public static void glVertexAttribI4u(int index, ByteBuffer v)
	{
		GL30.glVertexAttribI4u(index, v);
		
	}
	
	public static void glVertexAttribI4u(int index, IntBuffer v)
	{
		GL30.glVertexAttribI4u(index, v);
		
	}
	
	public static void glVertexAttribI4u(int index, ShortBuffer v)
	{
		GL30.glVertexAttribI4u(index, v);
		
	}
	
	public static void glVertexAttribI4ui(int index, int x, int y, int z, int w)
	{
		GL30.glVertexAttribI4ui(index, x, y, z, w);
		
	}
	
	public static void glVertexAttribIFormat(int attribindex, int size, int type, int relativeoffset)
	{
		GL43.glVertexAttribIFormat(attribindex, size, type, relativeoffset);
		
	}
	
	public static void glVertexAttribIPointer(int index, int size, int type, int stride, ByteBuffer buffer)
	{
		GL30.glVertexAttribIPointer(index, size, type, stride, buffer);
		
	}
	
	public static void glVertexAttribIPointer(int index, int size, int type, int stride, IntBuffer buffer)
	{
		GL30.glVertexAttribIPointer(index, size, type, stride, buffer);
		
	}
	
	public static void glVertexAttribIPointer(int index, int size, int type, int stride, long buffer_buffer_offset)
	{
		GL30.glVertexAttribIPointer(index, size, type, stride, buffer_buffer_offset);
		
	}
	
	public static void glVertexAttribIPointer(int index, int size, int type, int stride, ShortBuffer buffer)
	{
		GL30.glVertexAttribIPointer(index, size, type, stride, buffer);
		
	}
	
	public static void glVertexAttribL1(int index, DoubleBuffer v)
	{
		GL41.glVertexAttribL1(index, v);
		
	}
	
	public static void glVertexAttribL1d(int index, double x)
	{
		GL41.glVertexAttribL1d(index, x);
		
	}
	
	public static void glVertexAttribL2(int index, DoubleBuffer v)
	{
		GL41.glVertexAttribL2(index, v);
		
	}
	
	public static void glVertexAttribL2d(int index, double x, double y)
	{
		GL41.glVertexAttribL2d(index, x, y);
		
	}
	
	public static void glVertexAttribL3(int index, DoubleBuffer v)
	{
		GL41.glVertexAttribL3(index, v);
		
	}
	
	public static void glVertexAttribL3d(int index, double x, double y, double z)
	{
		GL41.glVertexAttribL3d(index, x, y, z);
		
	}
	
	public static void glVertexAttribL4(int index, DoubleBuffer v)
	{
		GL41.glVertexAttribL4(index, v);
		
	}
	
	public static void glVertexAttribL4d(int index, double x, double y, double z, double w)
	{
		GL41.glVertexAttribL4d(index, x, y, z, w);
		
	}
	
	public static void glVertexAttribLFormat(int attribindex, int size, int type, int relativeoffset)
	{
		GL43.glVertexAttribLFormat(attribindex, size, type, relativeoffset);
		
	}
	
	public static void glVertexAttribLPointer(int index, int size, int stride, DoubleBuffer pointer)
	{
		GL41.glVertexAttribLPointer(index, size, stride, pointer);
		
	}
	
	public static void glVertexAttribLPointer(int index, int size, int stride, long pointer_buffer_offset)
	{
		GL41.glVertexAttribLPointer(index, size, stride, pointer_buffer_offset);
		
	}
	
	public static void glVertexAttribP1u(int index, int type, boolean normalized, IntBuffer value)
	{
		GL33.glVertexAttribP1u(index, type, normalized, value);
		
	}
	
	public static void glVertexAttribP1ui(int index, int type, boolean normalized, int value)
	{
		GL33.glVertexAttribP1ui(index, type, normalized, value);
		
	}
	
	public static void glVertexAttribP2u(int index, int type, boolean normalized, IntBuffer value)
	{
		GL33.glVertexAttribP2u(index, type, normalized, value);
		
	}
	
	public static void glVertexAttribP2ui(int index, int type, boolean normalized, int value)
	{
		GL33.glVertexAttribP2ui(index, type, normalized, value);
		
	}
	
	public static void glVertexAttribP3u(int index, int type, boolean normalized, IntBuffer value)
	{
		GL33.glVertexAttribP3u(index, type, normalized, value);
		
	}
	
	public static void glVertexAttribP3ui(int index, int type, boolean normalized, int value)
	{
		GL33.glVertexAttribP3ui(index, type, normalized, value);
		
	}
	
	public static void glVertexAttribP4u(int index, int type, boolean normalized, IntBuffer value)
	{
		GL33.glVertexAttribP4u(index, type, normalized, value);
		
	}
	
	public static void glVertexAttribP4ui(int index, int type, boolean normalized, int value)
	{
		GL33.glVertexAttribP4ui(index, type, normalized, value);
		
	}
	
	public static void glVertexAttribPointer(int index, int size, boolean unsigned, boolean normalized, int stride, ByteBuffer buffer)
	{
		GL20.glVertexAttribPointer(index, size, unsigned, normalized, stride, buffer);
		
	}
	
	public static void glVertexAttribPointer(int index, int size, boolean unsigned, boolean normalized, int stride, IntBuffer buffer)
	{
		GL20.glVertexAttribPointer(index, size, unsigned, normalized, stride, buffer);
		
	}
	
	public static void glVertexAttribPointer(int index, int size, boolean unsigned, boolean normalized, int stride, ShortBuffer buffer)
	{
		GL20.glVertexAttribPointer(index, size, unsigned, normalized, stride, buffer);
		
	}
	
	public static void glVertexAttribPointer(int index, int size, boolean normalized, int stride, DoubleBuffer buffer)
	{
		GL20.glVertexAttribPointer(index, size, normalized, stride, buffer);
		
	}
	
	public static void glVertexAttribPointer(int index, int size, boolean normalized, int stride, FloatBuffer buffer)
	{
		GL20.glVertexAttribPointer(index, size, normalized, stride, buffer);
		
	}
	
	public static void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, ByteBuffer buffer)
	{
		GL20.glVertexAttribPointer(index, size, type, normalized, stride, buffer);
		
	}
	
	public static void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long buffer_buffer_offset)
	{
		GL20.glVertexAttribPointer(index, size, type, normalized, stride, buffer_buffer_offset);
		
	}
	
	public static void glVertexBindingDivisor(int bindingindex, int divisor)
	{
		GL43.glVertexBindingDivisor(bindingindex, divisor);
		
	}
	
	public static void glVertexP2u(int type, IntBuffer value)
	{
		GL33.glVertexP2u(type, value);
		
	}
	
	public static void glVertexP2ui(int type, int value)
	{
		GL33.glVertexP2ui(type, value);
		
	}
	
	public static void glVertexP3u(int type, IntBuffer value)
	{
		GL33.glVertexP3u(type, value);
		
	}
	
	public static void glVertexP3ui(int type, int value)
	{
		GL33.glVertexP3ui(type, value);
		
	}
	
	public static void glVertexP4u(int type, IntBuffer value)
	{
		GL33.glVertexP4u(type, value);
		
	}
	
	public static void glVertexP4ui(int type, int value)
	{
		GL33.glVertexP4ui(type, value);
		
	}
	
	public static void glVertexPointer(int size, int stride, DoubleBuffer pointer)
	{
		GL11.glVertexPointer(size, stride, pointer);
		
	}
	
	public static void glVertexPointer(int size, int stride, FloatBuffer pointer)
	{
		GL11.glVertexPointer(size, stride, pointer);
		
	}
	
	public static void glVertexPointer(int size, int type, int stride, ByteBuffer pointer)
	{
		GL11.glVertexPointer(size, type, stride, pointer);
		
	}
	
	public static void glVertexPointer(int size, int type, int stride, long pointer_buffer_offset)
	{
		GL11.glVertexPointer(size, type, stride, pointer_buffer_offset);
		
	}
	
	public static void glVertexPointer(int size, int stride, IntBuffer pointer)
	{
		GL11.glVertexPointer(size, stride, pointer);
		
	}
	
	public static void glVertexPointer(int size, int stride, ShortBuffer pointer)
	{
		GL11.glVertexPointer(size, stride, pointer);
		
	}
	
	public static void glViewport(int x, int y, int width, int height)
	{
		GL11.glViewport(x, y, width, height);
		
	}
	
	public static void glViewportArray(int first, FloatBuffer v)
	{
		GL41.glViewportArray(first, v);
		
	}
	
	public static void glViewportIndexed(int index, FloatBuffer v)
	{
		GL41.glViewportIndexed(index, v);
		
	}
	
	public static void glViewportIndexedf(int index, float x, float y, float w, float h)
	{
		GL41.glViewportIndexedf(index, x, y, w, h);
		
	}
	
	//GL W
	
	public static void glWaitSync(GLSync sync, int flags, long timeout)
	{
		GL32.glWaitSync(sync, flags, timeout);
		
	}
	
}
*/